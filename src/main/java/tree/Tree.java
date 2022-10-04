package tree;

import resource.implementation.InformationResource;

import javax.swing.tree.DefaultTreeModel;

public interface Tree {

    DefaultTreeModel generateTree(InformationResource informationResource);

}
