/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.utils;

import dk.frv.eavdam.data.FATDMACell;	
import dk.frv.eavdam.data.FATDMADefaultChannel;	
import dk.frv.eavdam.data.FATDMANode;
import dk.frv.eavdam.io.DefaultFATDMAReader;
import dk.frv.eavdam.layers.FATDMAGridLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for FATDMA calculations.
 */
public class FATDMAUtils {

	/**
	 * Holds default FATDMA cell values.
	 */
	public static Map<String,List<FATDMACell>> fatdmaCellsMap = null;
	
	/**
	 * Checks whether given reserved blocks are according to IALA A-124 default FATDMA scheme in the given latitude/longitude point.
	 *
	 * @param lat                        Latitude for a point
	 * @param lon                        Longitude for a point
	 * @param reservedBlocksForChannelA  Reserved blocks for channel A
	 * @param reservedBlocksForChannelB  Reserved blocks for channel B
	 * @return                           True if the reserved blocks are according to the IALA A-124 default FATDMA scheme, false otherwise
	 */
	public static boolean areReservedBlocksAccordingToFATDMAScheme(double lat, double lon, List<Integer> reservedBlocksForChannelA, List<Integer> reservedBlocksForChannelB) {
	
		int singleCellSizeInNauticalMiles = 30;
		int noOfSingleCellsAlongOneSideOfMasterCell = 6;		
		int cellNumber =  FATDMAGridLayer.calculateCellNo(singleCellSizeInNauticalMiles, noOfSingleCellsAlongOneSideOfMasterCell, lat, lon);									
		
		if (fatdmaCellsMap == null) {
			fatdmaCellsMap = DefaultFATDMAReader.readDefaultValues(null, null);
		}
		
		List<Integer> acceptedFATDMABlocksForChannelA = new ArrayList<Integer>();
		List<Integer> acceptedFATDMABlocksForChannelB = new ArrayList<Integer>();
		
		List<FATDMACell> fatdmaICells = fatdmaCellsMap.get(String.valueOf(cellNumber) + "-I");
		acceptedFATDMABlocksForChannelA = processFATDMACellsChannelA(fatdmaICells, acceptedFATDMABlocksForChannelA);		
		acceptedFATDMABlocksForChannelB = processFATDMACellsChannelB(fatdmaICells, acceptedFATDMABlocksForChannelB);
		List<FATDMACell> fatdmaIICells = fatdmaCellsMap.get(String.valueOf(cellNumber) + "-II");		
		acceptedFATDMABlocksForChannelA = processFATDMACellsChannelA(fatdmaIICells, acceptedFATDMABlocksForChannelA);
		acceptedFATDMABlocksForChannelB = processFATDMACellsChannelB(fatdmaIICells, acceptedFATDMABlocksForChannelB);

		if (reservedBlocksForChannelA != null) {
			for (Integer block : reservedBlocksForChannelA) {
				if (!acceptedFATDMABlocksForChannelA.contains(block)) {
					return false;
				}
			}
		}

		if (reservedBlocksForChannelB != null) {
			for (Integer block : reservedBlocksForChannelB) {
				if (!acceptedFATDMABlocksForChannelB.contains(block)) {			
					return false;
				}
			}
		}

		return true;		
	}
	
	private static List<Integer> processFATDMACellsChannelA(List<FATDMACell> fatdmaCells, List<Integer> acceptedFATDMABlocks) {
		if(fatdmaCells == null) return new ArrayList<Integer>();
		if(acceptedFATDMABlocks == null) acceptedFATDMABlocks = new ArrayList<Integer>();
		
		for (FATDMACell fatdmaCell : fatdmaCells) {
			
			FATDMADefaultChannel channelA = fatdmaCell.getChannelA();
			
			FATDMANode channelASemaphoreNode = channelA.getSemaphoreNode();
			List<Integer> channelASemaphoreNodeAcceptedFATDMABlocks =
				getBlocks(channelASemaphoreNode.getStartingSlot(), channelASemaphoreNode.getBlockSize(), channelASemaphoreNode.getIncrement());
			for (Integer block : channelASemaphoreNodeAcceptedFATDMABlocks) {
				if (!acceptedFATDMABlocks.contains(block)) {
					acceptedFATDMABlocks.add(block);
				}
			}
			
			FATDMANode channelANonSemaphoreNode = channelA.getNonSemaphoreNode();
			List<Integer> channelANonSemaphoreNodeAcceptedFATDMABlocks =
				getBlocks(channelANonSemaphoreNode.getStartingSlot(), channelANonSemaphoreNode.getBlockSize(), channelANonSemaphoreNode.getIncrement());
			for (Integer block : channelANonSemaphoreNodeAcceptedFATDMABlocks) {
				if (!acceptedFATDMABlocks.contains(block)) {
					acceptedFATDMABlocks.add(block);
				}
			}
			
		}
		
		return acceptedFATDMABlocks;
	
	}
			
	private static List<Integer> processFATDMACellsChannelB(List<FATDMACell> fatdmaCells, List<Integer> acceptedFATDMABlocks) {
	
		if(fatdmaCells == null) return new ArrayList<Integer>();
		if(acceptedFATDMABlocks == null) acceptedFATDMABlocks = new ArrayList<Integer>();
		
		for (FATDMACell fatdmaCell : fatdmaCells) {	
	
			FATDMADefaultChannel channelB = fatdmaCell.getChannelB();		

			FATDMANode channelBSemaphoreNode = channelB.getSemaphoreNode();
			List<Integer> channelBSemaphoreNodeAcceptedFATDMABlocks =
				getBlocks(channelBSemaphoreNode.getStartingSlot(), channelBSemaphoreNode.getBlockSize(), channelBSemaphoreNode.getIncrement());
			for (Integer block : channelBSemaphoreNodeAcceptedFATDMABlocks) {
				if (!acceptedFATDMABlocks.contains(block)) {
					acceptedFATDMABlocks.add(block);
				}
			}
			
			FATDMANode channelBNonSemaphoreNode = channelB.getNonSemaphoreNode();
			List<Integer> channelBNonSemaphoreNodeAcceptedFATDMABlocks =
				getBlocks(channelBNonSemaphoreNode.getStartingSlot(), channelBNonSemaphoreNode.getBlockSize(), channelBNonSemaphoreNode.getIncrement());
			for (Integer block : channelBNonSemaphoreNodeAcceptedFATDMABlocks) {
				if (!acceptedFATDMABlocks.contains(block)) {
					acceptedFATDMABlocks.add(block);
				}
			}
			
		}
		
		return acceptedFATDMABlocks;
	}
	
	/**
	 * Calculates block numbers that given startslot, block size and increment values define.
	 *
	 * @param startslot   Startslot
	 * @param blockSize   Block size
	 * @param increment  Increment
	 * @return            Block numbers that the given startslot, block size and increment values define
	 */
	public static List<Integer> getBlocks(Integer startslot, Integer blockSize, Integer increment) {
	
		List<Integer> blocks = new ArrayList<Integer>();
	
		if (startslot != null && blockSize != null && increment != null) {
			int startslotInt = startslot.intValue();
			int blockSizeInt = blockSize.intValue();
			int incrementInt = increment.intValue();
			
			int i = 0;
			boolean goingAround = false;
			while (i*incrementInt <= 2249) {							
				Integer slot = null;
				for (int j=0; j<blockSizeInt; j++) {
					slot = new Integer(startslotInt+j+(i*incrementInt));
					if (slot.intValue() > 2249) {
						slot = new Integer(slot.intValue()-2250);
						goingAround = true;
					}
					if (goingAround && slot.intValue() >= startslotInt) {
						break;
					}
					if (!blocks.contains(slot)) {										
						blocks.add(slot);
					}
				}
				if (incrementInt == 0 || (goingAround && slot.intValue() >= startslotInt)) {
					break;
				}				
				i++;
			}
		}
		
		return blocks;	
	}
			
}