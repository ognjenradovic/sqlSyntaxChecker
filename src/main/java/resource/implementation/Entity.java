package resource.implementation;

import lombok.Getter;
import lombok.Setter;
import resource.DBNode;
import resource.DBNodeComposite;

import java.util.ArrayList;

@Getter
@Setter
public class Entity extends DBNodeComposite {
    public Entity(String name, DBNode parent) {
        super(name, parent);
        primaryKeys=new ArrayList<>();
        foreignKeys=new ArrayList<>();
    }
    private ArrayList<String> primaryKeys;
    private ArrayList<String> foreignKeys;


    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Attribute){
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
        }

    }

    public ArrayList<String> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ArrayList<String> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public ArrayList<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(ArrayList<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
}
