//ShowTree Tree Visualization System
//Copyright (C) 2009 Yuvi Masory
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation, version 3 only.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

package logic.treeIterators;

import logic.Node;

/*
 * Iterates through a tree in-order.
 * If the tree has arity greater than two the algorithm will visit either floor(numChildren/2) before the
 * present Node, or ceiling(numChildren/2), depending on provided argument.
 * 
 * NOTE: Iterators must be called on the root of a tree (its parent must be null) or unexpected behavior
 * will ensue
 */
public class InorderIterator extends TreeIterator {
	
	private Node current;
	
	private boolean leftHeavy;
	
	public InorderIterator(Node root, boolean leftHeavy) {
		super(root);
		current = root;
		this.leftHeavy = leftHeavy;
		iterate();
	}

	public boolean hasNext() {
		if(current == null) {
			return false;
		}
		return true;
	}

	public Node next() {
		Node tmp = current;
		iterate();
		return tmp;
	}
	
	private void iterate() {
		Node tmp = current;
		while(true) {
			if(tmp == null) {
				current = null;
				return;
			}
			
			int firstHalf;
			if(leftHeavy) {
				firstHalf = (int) Math.ceil(((double) tmp.degree()) / 2);
			}
			else {
				firstHalf = (int) Math.floor(((double) tmp.degree()) / 2);				
			}
			
			int unvisitedChildIndex = -1;
			for(int i = 0; i < firstHalf; i++) {
				if(tmp.getChildren().get(i).isVisited() == false) {
					unvisitedChildIndex = i;
					break;
				}
			}
			if(unvisitedChildIndex > -1) {
				tmp = tmp.getChildren().get(unvisitedChildIndex);
			}
			
			else {
				if(tmp.isVisited() == false) {
					tmp.visit();
					current = tmp;
					return;
				}
				else {
					for(int i = firstHalf; i < tmp.degree(); i++) {
						if(tmp.getChildren().get(i).isVisited() == false) {
							unvisitedChildIndex = i;
							break;
						}
					}
					if(unvisitedChildIndex > -1) {
						tmp = tmp.getChildren().get(unvisitedChildIndex);
					}
					else {
						tmp = tmp.getParent();
					}
				}
			}
		}
	}

	/*
	 * Not available, throws exception
	 * (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
