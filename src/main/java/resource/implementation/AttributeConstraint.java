package resource.implementation;

import lombok.Getter;
import lombok.Setter;
import resource.DBNode;
import controller.enums.ConstraintType;

@Getter
@Setter
public class AttributeConstraint extends DBNode {

    private ConstraintType constraintType;

    public AttributeConstraint(String name, DBNode parent, ConstraintType constraintType) {
        super(name, parent);
        this.constraintType = constraintType;
    }

}
