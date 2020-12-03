package fr.istic.prg1.tree;

import java.util.Scanner;

import fr.istic.prg1.tree.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		if(this.isEmpty()){return false;}
		Iterator<Node> it = this.iterator();
		
		int i1=0;	//axe x
		int i2 = 255;
		int imiddle;
		
		int j1=0;	//axe y
		int j2 = 255;
		int jmiddle;
		
		boolean isHorizontal = true;
		
		while (it.getValue().state == 2)
		{
			if(isHorizontal)
			{
				jmiddle = (j2 + j1) / 2;
				if(y <= jmiddle)
				{
					it.goLeft();
					j2 = jmiddle;
				}
				else
				{
					it.goRight();
					j1 = jmiddle + 1;
				}
				
			}
			else
			{
				imiddle = (i2 + i1) / 2;
				if(x <= imiddle)
				{
					it.goLeft();
					i2 = imiddle;
				}
				else
				{
					it.goRight();
					i1 = imiddle + 1;
				}
			}
			isHorizontal = !isHorizontal;
		}
		return (it.getValue().state == 1);
	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2
	 *            image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
				Iterator<Node> it1 = image2.iterator();
				it.clear();
				affectAux(it, it1);
	}

	public void affectAux(Iterator<Node> it, Iterator<Node> it1)
	{
		switch(it1.nodeType())
		{
			case DOUBLE: 
				it.addValue(Node.valueOf(2));
				it.goLeft();
				it1.goLeft();
				affectAux(it,it1);
				it.goUp();
				it1.goUp();
				it.goRight();
				it1.goRight();
				affectAux (it, it1);
				it.goUp();
				it1.goUp();
				break;
			case LEAF:
				it.addValue(it1.getValue());
				break ;

			default: System.out.println("Impossible d’affecter") ;
		}
	}
	
	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) 
	{
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image2.iterator();
		it.clear();
		rotate180Aux(it, it1);
	}

	private void rotate180Aux(Iterator<Node> it, Iterator<Node> it1) 
	{
		switch(it1.nodeType())
		{
			case DOUBLE: 
				it.addValue(Node.valueOf(2));
				it.goRight();
				it1.goLeft();
				rotate180Aux(it,it1);
				it.goUp();
				it1.goUp();
				it.goLeft();
				it1.goRight();
				rotate180Aux(it, it1);
				it.goUp();
				it1.goUp();
				break;
			case LEAF:
				it.addValue(it1.getValue());
				break ;

			default: System.out.println("Impossible de rotate") ;
		}
		
	}
	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction non demeand�e");
		System.out.println("-------------------------------------------------");
		System.out.println();	    
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		Iterator<Node> it = this.iterator();
		videoInverseAux(it);
	}
	

	private void videoInverseAux(Iterator<Node> it) 
	{
		switch(it.nodeType())
		{
			case DOUBLE: 
				it.goLeft();
				videoInverseAux(it);
				it.goUp();
				it.goRight();
				videoInverseAux(it);
				it.goUp();
				break;
			case LEAF:
				if(it.getValue().state == 1)
				{
					it.remove();
					it.addValue(Node.valueOf(0));
				}
				else
				{
					it.remove();
					it.addValue(Node.valueOf(1));
				}
				break ;

			default: System.out.println("Impossible d'inverser") ;
		}
		
	}
//it.switchValue((it.getValue().state == 1 ? 0 : 1 ));
	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image2.iterator();
		it.clear();
		mirrorVAux(it, it1, false);
	}

	private void mirrorVAux(Iterator<Node> it, Iterator<Node> it1, boolean isHorizontal) 
	{
		switch(it1.nodeType())
		{
			case DOUBLE:
				if(isHorizontal)
				{
					it.addValue(Node.valueOf(2));
					it.goLeft();
					it1.goLeft();
					mirrorVAux(it,it1,!isHorizontal);
					it.goUp();
					it1.goUp();
					it.goRight();
					it1.goRight();
				}
				else
				{
					
					it.addValue(Node.valueOf(2));
					it.goRight();
					it1.goLeft();
					mirrorVAux(it,it1,!isHorizontal);
					it.goUp();
					it1.goUp();
					it.goLeft();
					it1.goRight();
				}
				
				mirrorVAux (it, it1, !isHorizontal);
				it.goUp();
				it1.goUp();
				break;
			case LEAF:
				it.addValue(it1.getValue());
				break ;

			default: System.out.println("Impossible de créer une image miroir") ;
		}
		
	}
	
	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image2.iterator();
		it.clear();
		mirrorHAux(it, it1, true);
	}

	private void mirrorHAux(Iterator<Node> it, Iterator<Node> it1, boolean isHorizontal) 
	{
		switch(it1.nodeType())
		{
			case DOUBLE:
				if(isHorizontal)
				{
					it.addValue(Node.valueOf(2));
					it.goLeft();
					it1.goLeft();
					mirrorHAux(it,it1,!isHorizontal);
					it.goUp();
					it1.goUp();
					it.goRight();
					it1.goRight();
				}
				else
				{
					
					it.addValue(Node.valueOf(2));
					it.goRight();
					it1.goLeft();
					mirrorHAux(it,it1,!isHorizontal);
					it.goUp();
					it1.goUp();
					it.goLeft();
					it1.goRight();
				}
				
				mirrorHAux (it, it1, !isHorizontal);
				it.goUp();
				it1.goUp();
				break;
			case LEAF:
				it.addValue(it1.getValue());
				break ;

			default: System.out.println("Impossible de créer une image miroir") ;
		}
		
	}
	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) 
	{
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image2.iterator();
		it.clear();
		if(it1.nodeType() == NodeType.DOUBLE)
		{
			it1.goLeft();
			if(it1.nodeType() == NodeType.DOUBLE)
			{
				it1.goLeft();
				
			}
		}
		affectAux(it, it1);
		
	}

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this
	 * devient éteint.
	 * 
	 * @param image2
	 *            image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image2.iterator();
		if(it1.nodeType() == NodeType.DOUBLE)
		{
			it.clear();
			it.addValue(Node.valueOf(2));
			it.goRight();
			it.addValue(Node.valueOf(0));
			it.goUp();
			it.goLeft();
			it.addValue(Node.valueOf(2));
			it.goRight();
			it.addValue(Node.valueOf(0));
			it.goUp();
			it.goLeft();
		}
		zoomOutAux(it, it1, 1);
		it.goUp();
		checkChild(it);
		it.goUp();
		checkChild(it);
	}
	
	private void zoomOutAux(Iterator<Node> it, Iterator<Node> it1, int profondeur)
	{
		if(profondeur < 15)
		{
			switch(it1.nodeType())
			{
				case DOUBLE: 
					it.addValue(Node.valueOf(2));
					it.goLeft();
					it1.goLeft();
					zoomOutAux(it,it1,profondeur+1);
					it.goUp();
					it1.goUp();
					it.goRight();
					it1.goRight();
					zoomOutAux(it, it1,profondeur+1);
					it.goUp();
					it1.goUp();
					break;
				case LEAF:
					it.addValue(it1.getValue());
					break ;
	
				default: System.out.println("Impossible d’affecter") ;
			}
		}
		else
		{
			it.addValue(Node.valueOf( branchColor(it1)));
		}
		checkChild(it);
	}
	
	private int branchColor(Iterator<Node> it)
	{
		switch(it.nodeType())
		{
			case DOUBLE: 
                it.goLeft();
                int value1 = it.getValue().state;
                it.goUp();
                it.goRight();
                int value2 = it.getValue().state;
                it.goUp();
                if ((value1 == 0 || value2 == 0 ) && !(value1 == 1 || value2 ==1))
				{
					return 0;
				}
				else
				{
					return 1;
				}
			case LEAF:
				return it.getValue().state;

			default: System.out.println("Impossible d’affecter") ;
		}
		return 0;
	}
	
	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		if(!image1.isEmpty() && !image2.isEmpty())
		{
			Iterator<Node> it = this.iterator();
	    	Iterator<Node> it1 = image1.iterator();
	    	Iterator<Node> it2 = image2.iterator();
	    	it.clear(); 
	    	intersectionAux(it, it1, it2);
		}
		
	}
	
	private void intersectionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2)
	{
		if (it1.getValue().state == 0 || it2.getValue().state == 0)
		{
			it.addValue(Node.valueOf(0));
		}
		else if (it1.getValue().state == 1 && it2.getValue().state == 1)
		{
			it.addValue(Node.valueOf(1));
		}
		else if(it1.getValue().state == 2 && it2.getValue().state == 2)
		{
			it.addValue(Node.valueOf(2));
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			intersectionAux(it,it1,it2);
			it.goUp();
			it1.goUp();
			it2.goUp();
			it.goRight();
			it1.goRight();
			it2.goRight();
			intersectionAux(it,it1,it2);
			it.goUp();
			it1.goUp();
			it2.goUp();
		}
		else if (it1.getValue().state == 2)
		{
	        affectAux(it,it1);
		}
		else
		{
	        affectAux(it,it2);
		}
		checkChild(it);
	}
	
	
	private void checkChild(Iterator<Node> it)
	{
		if(it.nodeType() == NodeType.DOUBLE)
		{
			it.goLeft();
			int a = it.getValue().state;
			it.goUp();
			it.goRight();
			int b = it.getValue().state;
			if(a != 2 && a == b)
			{
				it.remove();
				it.goUp();
				it.goLeft();
				it.remove();
				it.goUp();
				it.remove();
				it.addValue(Node.valueOf(a));
			}
			else
			{
				it.goUp();
			}
		}
	}


	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		if(!image1.isEmpty() && !image2.isEmpty())
		{
			Iterator<Node> it = this.iterator();
	    	Iterator<Node> it1 = image1.iterator();
	    	Iterator<Node> it2 = image2.iterator();
	    	it.clear(); 
	    	unionAux(it, it1, it2);
		}
		
	}
	
	private void unionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2)
	{
		if (it1.getValue().state == 1 || it2.getValue().state == 1)
		{
			it.addValue(Node.valueOf(1));
		}
		else if (it1.getValue().state == 0 && it2.getValue().state == 0)
		{
			it.addValue(Node.valueOf(0));
		}
		else if(it1.getValue().state == 2 && it2.getValue().state == 2)
		{
			it.addValue(Node.valueOf(2));
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			unionAux(it,it1,it2);
			it.goUp();
			it1.goUp();
			it2.goUp();
			it.goRight();
			it1.goRight();
			it2.goRight();
			unionAux(it,it1,it2);
			it.goUp();
			it1.goUp();
			it2.goUp();
		}
		else if (it1.getValue().state == 2)
		{
	        affectAux(it,it1);
		}
		else
		{
	        affectAux(it,it2);
		}
		if(it.nodeType() == NodeType.DOUBLE)
		{
			it.goLeft();
			int a = it.getValue().state;
			it.goUp();
			it.goRight();
			int b = it.getValue().state;
			if(a != 2 && a == b)
			{
				it.remove();
				it.goUp();
				it.goLeft();
				it.remove();
				it.goUp();
				it.remove();
				it.addValue(Node.valueOf(a));
			}
			else
			{
				it.goUp();
			}
		}
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		Iterator<Node> it = this.iterator();
		return testDiagonalAux(it);
	}
	
	private boolean testDiagonalAux(Iterator<Node> it)
	{
		boolean ret = true;
		if(it.nodeType() == NodeType.DOUBLE)
		{
			it.goLeft();
			boolean test;
			if(it.nodeType() == NodeType.DOUBLE)
			{
				it.goLeft();
				test = testDiagonalAux(it);
				it.goUp();
			}
			else
			{
				test = testDiagonalAux(it);
			}
			if(!test)
			{
				return false;
			}
			it.goRight();
			if(it.nodeType() == NodeType.DOUBLE)
			{
				it.goRight();
				test = testDiagonalAux(it);
				it.goUp();
			}
			else
			{
				test = testDiagonalAux(it);
			}
			return test;
		}
		else if(it.getValue().state==1)
		{
			it.goUp();
			return true;
		}
		else
		{
			it.remove();
			it.addValue(Node.valueOf(1));
			it.goUp();
			return false;
		}
	}

	/**
	 * @param x1
	 *            abscisse du premier point
	 * @param y1
	 *            ordonnée du premier point
	 * @param x2
	 *            abscisse du deuxième point
	 * @param y2
	 *            ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		if(this.isEmpty()){return false;}
		Iterator<Node> it = this.iterator();
		
		int i1=0;	//axe x
		int i2 = 255;
		int imiddle;
		
		int j1=0;	//axe y
		int j2 = 255;
		int jmiddle;
		
		boolean isHorizontal = true;
		
		while (it.getValue().state == 2)
		{
			if(isHorizontal)
			{
				jmiddle = (j2 + j1) / 2;
				if(y1 <= jmiddle && y2 <= jmiddle)
				{
					it.goLeft();
					j2 = jmiddle;
				}
				else if (y1 > jmiddle && y2 > jmiddle)
				{
					it.goRight();
					j1 = jmiddle + 1;
				}
				else
				{
					return false;
				}
			}
			else
			{
				imiddle = (i2 + i1) / 2;
				if(x1 <= imiddle && x2 <= imiddle)
				{
					it.goLeft();
					i2 = imiddle;
				}
				else if(x1 > imiddle && x2 > imiddle)
				{
					it.goRight();
					i1 = imiddle + 1;
				}
				else
				{
					return false;
				}
			}
			isHorizontal = !isHorizontal;
		}
		return true;
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) 
	{
		if(!image2.isEmpty() && !image2.isEmpty())
		{
			if(this == image2)
			{
				return true;
			}
			Iterator<Node> it = this.iterator();
	    	Iterator<Node> it1 = image2.iterator();
	    	return isIncludedAux(it, it1);
		}
		
		
	    return false;
	}
	
	private boolean isIncludedAux(Iterator<Node> it, Iterator<Node> it1)
	{
		boolean ret = true;
		if (it.getValue().state == 1 || it1.getValue().state == 0)
		{
			ret = false;
		}
		else if(it1.nodeType() == NodeType.DOUBLE && !(it.getValue().state == 0 || it1.getValue().state == 1))
		{
			it.goLeft();
			it1.goLeft();
			ret = isIncludedAux(it, it1);
			if(ret)
			{
				it.goUp();
				it1.goUp();
				it.goRight();
				it1.goRight();
				ret = isIncludedAux(it, it1);
			}
			it.goUp();
			it1.goUp();
		}
		return ret;
	}

}
