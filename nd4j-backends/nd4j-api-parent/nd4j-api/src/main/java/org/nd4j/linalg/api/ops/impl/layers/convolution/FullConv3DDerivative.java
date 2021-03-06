package org.nd4j.linalg.api.ops.impl.layers.convolution;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.layers.convolution.config.FullConv3DConfig;

import java.util.List;


/**
 * FullConv3DDerivative operation
 */
@Slf4j
public class FullConv3DDerivative extends FullConv3D {

    public FullConv3DDerivative() {}

    @Builder(builderMethodName = "derivativeBuilder")
    public FullConv3DDerivative(SameDiff sameDiff, SDVariable[] inputFunctions, INDArray[] inputs, INDArray[] outputs, FullConv3DConfig conv3DConfig) {
        super(sameDiff, inputFunctions, inputs, outputs, conv3DConfig);
    }

    @Override
    public String opName() {
        return "fullconv3d_bp";
    }


    @Override
    public List<SDVariable> doDiff(List<SDVariable> f1) {
        throw new UnsupportedOperationException("Unable to take derivative of derivative.");
    }

}
