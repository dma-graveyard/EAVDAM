package dk.frv.eavdam.layers;

import com.bbn.openmap.layer.shape.ShapeLayer;

public class OMAISShapeLayer extends ShapeLayer {

	private static final long serialVersionUID = 1L;
		
    public OMAISShapeLayer() {}

	public OMAISShapeLayer(String shapeFileName) {
		super(shapeFileName);
	}

}
    