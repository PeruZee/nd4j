package org.nd4j.linalg.api.ops.impl.shape;

import lombok.val;
import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.imports.descriptors.properties.PropertyMapping;
import org.nd4j.linalg.api.ops.DynamicCustomOp;
import org.tensorflow.framework.AttrValue;
import org.tensorflow.framework.GraphDef;
import org.tensorflow.framework.NodeDef;

import java.util.*;

/**
 *
 */
public class Squeeze extends DynamicCustomOp {

    private int[] squeezeDims;

    public Squeeze(){
    }

    public Squeeze(SameDiff sameDiff, SDVariable arg, int[] squeezeDims){
        super(null, sameDiff, new SDVariable[]{arg});
        this.squeezeDims = squeezeDims;
    }

    @Override
    public void initFromTensorFlow(NodeDef nodeDef, SameDiff initWith, Map<String, AttrValue> attributesForNode, GraphDef graph) {
        super.initFromTensorFlow(nodeDef, initWith, attributesForNode, graph);
        if(squeezeDims != null)
            addIArgument(squeezeDims);
    }

    @Override
    public void resolvePropertiesFromSameDiffBeforeExecution() {
        super.resolvePropertiesFromSameDiffBeforeExecution();
        if(squeezeDims != null && numIArguments() < squeezeDims.length) {
            addIArgument(squeezeDims);
        }
    }

    @Override
    public String opName() {
        return "squeeze";
    }

    @Override
    public String tensorflowName() {
        return "Squeeze";
    }

    @Override
    public Map<String, Map<String, PropertyMapping>> mappingsForFunction() {
        Map<String, Map<String, PropertyMapping>> ret = new HashMap<>();
        Map<String,PropertyMapping> mapping = new LinkedHashMap<>();
        val squeezeDims = PropertyMapping.builder()
                .tfAttrName("squeeze_dims")
                .propertyNames(new String[]{"squeezeDims"})
                .build();
        mapping.put("squeezeDims",squeezeDims);
        ret.put(tensorflowName(),mapping);
        return ret;
    }

    @Override
    public List<SDVariable> doDiff(List<SDVariable> i_v){
        if(squeezeDims == null){
            //TODO Strictly speaking this *is* possible by inspecting the input array
            throw new IllegalStateException("Cannot do Squeeze backprop with no dimensions");
        }
        SDVariable ret = i_v.get(0);
        for(int d : squeezeDims){
            ret = sameDiff.expandDims(ret, d);
        }
        return Collections.singletonList(ret);
    }
}
