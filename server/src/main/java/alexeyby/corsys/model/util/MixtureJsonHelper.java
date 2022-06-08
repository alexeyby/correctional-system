package alexeyby.corsys.model.util;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class MixtureJsonHelper {
	public static  void putIntOrDouble(ObjectNode node, String property, double value) {
        int intValue = (int)value;
        
        if (value == intValue) {
        	node.put(property, intValue);
        } else {
        	node.put(property, value);
        }
	}
}