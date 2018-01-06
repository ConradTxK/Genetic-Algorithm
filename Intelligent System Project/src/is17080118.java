
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.JOptionPane;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.math.*;
import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;



class EvenOddRenderer implements TableCellRenderer {

	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Color foreground, background;

		double val = Double.parseDouble(value.toString());
		val = 255 - 255 * ((double) val / (double) is17080118.max);
		Color col = new Color((int) val, 0, 0);
		foreground = Color.WHITE;
		background = col;

		renderer.setForeground(foreground);
		renderer.setBackground(background);
		return renderer;
	}
}
//Read the array in input.txt
class TextReader //read input.txt file
{
	public static int getNumberOfRows(String textFilePath)
	{
		int count = 0;
		try
		{
			File file = new File(textFilePath);
			FileInputStream fis = new FileInputStream(file);
			Scanner scanner = new Scanner(fis);
			while(scanner.hasNextLine())
			{
				scanner.nextLine();
				count++;
			}
			scanner.close();
			
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	public static int getNumberOfColumns(String textFilePath,int lineNumber,String separator)
	{
		int count = 0;
		try
		{
			File file = new File(textFilePath);
			FileInputStream fis = new FileInputStream(file);
			Scanner scanner = new Scanner(fis);
			for(int l = 0; l<lineNumber;l++)
			{
				if(scanner.hasNextLine())
				{
					scanner.nextLine();
				}
			}
			String[] lineArray = scanner.nextLine().split(separator);
			count = lineArray.length;
			scanner.close();
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "the input file is wrong (the number of row must be equal to the number of column) please restart the program again.", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
		}
		return count;
	}
	public static int[][] getTwoDimensionalArray(String textFilePath, String separator,int row,int column)
	{
		try
		{
			int[][] result = new int[row][column];
			String[] columnResult = new String[column];
			File file = new File(textFilePath);
			FileInputStream fis = new FileInputStream(file);
			Scanner scanner = new Scanner(fis);
			for(int i = 0;i < row;i++)
			{
				if(scanner.hasNextLine())
				{
					columnResult = scanner.nextLine().split(separator);
					for(int j = 0;j<column;j++)
					{
						result[i][j] = Integer.parseInt(columnResult[j]);
					}
				}
			}
			scanner.close();
			return result;
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "the input file is wrong (the number of row must be equal to the number of column) please restart the program again.", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			
		}
		return null;
	}
	
}

class TwoGenertations //store two orderings and similarity matrix for H generation and last generation.
{
	private int[][] H_Ordering;
	private int[][] veryLast_Ordering;
	private int[][] similarity_H;
	private int[][] similarity_Gen;
	public int[][] getH_Ordering() {
		return H_Ordering;
	}
	public void setH_Ordering(int[][] h_Ordering) {
		H_Ordering = h_Ordering;
	}
	public int[][] getVeryLast_Ordering() {
		return veryLast_Ordering;
	}
	public void setVeryLast_Ordering(int[][] veryLast_Ordering) {
		this.veryLast_Ordering = veryLast_Ordering;
	}
	
	public int[][] getSimilarity_H() {
		return similarity_H;
	}
	public void setSimilarity_H(int[][] similarity_H) {
		this.similarity_H = similarity_H;
	}
	public int[][] getSimilarity_Gen() {
		return similarity_Gen;
	}
	public void setSimilarity_Gen(int[][] similarity_Gen) {
		this.similarity_Gen = similarity_Gen;
	}
}

public class is17080118 {
	/* initialize different attributes*/
	public static int max = 0;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//get array from input.txt
		int row;
		int column;
		int N;
		int Cr = 0;
		int Mu = 0;
		int Gen = 0;
		int H = 0;
		int P = 0;
		boolean check;
		TwoGenertations tg = new TwoGenertations(); //for 2nd and 3rd heat map
		int[][] H_Ordering;
		int[][] VeryLast_Ordering;
		String textPath = "input.txt"; //insert your path
		row = TextReader.getNumberOfRows(textPath);
		column = TextReader.getNumberOfColumns(textPath, 1, "  ");
		if(row == 0||column == 0||row!=column)
		{
			JOptionPane.showMessageDialog(null, "the input file is wrong (the number of row must be equal to the number of column) please restart the program again.", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		N = row; 
		int[][] initial_similarity = new int[N][N];
		initial_similarity = TextReader.getTwoDimensionalArray(textPath,"  ",row,column);
        check = checkDiagonalLine(initial_similarity,row);
        if(check == false)
        {
        	JOptionPane.showMessageDialog(null, "All the member on the diagonal line have to be initially zero", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        initial_similarity = caculateAverageNumberForDiagonalLine(initial_similarity,row);
      //initialize the attributes
		P = initializeTheSizeofPopulation(row);
		Gen = initializeTheNumberofGenerations();
		Cr = initializeTheProbabilityForCrossover();
		Mu = initializeTheProbabilityForMutation(Cr);
		H = definedGenerationNumber(Gen);
		//createOrderings
		tg = createOrderings(P,N,H,Cr,Mu,Gen,initial_similarity);
		H_Ordering = tg.getH_Ordering();
		VeryLast_Ordering = tg.getVeryLast_Ordering();
		//create heap map
		int[] ordering = new int [N];   /*ordering in tablecreation*/
		for(int i =0 ;i<N;i++) //initial similarity
		{
			ordering[i] = i+1;
		}
		max = 9;  // Find the maximum value in the similarity matrix. It is needed in order to encode the colors
		TableCreation(initial_similarity, ordering.length, ordering); // send your data for heat map visualization. Generation 0
		for(int j =0 ;j<N;j++) //initial similarity
		{
			ordering[j] = H_Ordering[0][j];
		}
		TableCreation(tg.getSimilarity_H(), ordering.length, ordering);// send your data for heat map visualization. Generation H
		for(int k =0 ;k<N;k++) //initial similarity
		{
			ordering[k] = VeryLast_Ordering[0][k];
		}
		TableCreation(tg.getSimilarity_Gen(), ordering.length, ordering);// send your data for heat map visualization. Generation VeryLast
		
	}//end main
	
	public static int[][] convert_Gen_Orderings(int P,int N,List<int[]>Gen_Orderings) 
	{
		int[][] result = new int[P][N];
		for(int i = 0;i<P;i++)
		{
			for(int j = 0;j<N;j++)
			{
				if(j!=36)
				{
					result[i][j] = Gen_Orderings.get(i)[j];
				}
				else
				{
					result[i][j] = 0;
				}
				
			}
		}
		
		return result;
	}
	public static TwoGenertations createOrderings(int P,int N,int H,int Cr,int Mu,int Gen,int[][] initial_similarity) //generate H generations and last generations
	{
				int[] one_ordering;
				int[][] newOrderings;
				List<int[]> Gen_Orderings = new ArrayList<>();
				int[][] H_similarity = new int[N][N];
				int[][] Gen_similarity = new int[N][N];
				int[] ord_row = new int[N];
				int[] ord_column = new int[N];
				TwoGenertations tg = new TwoGenertations();
		         //Get Orderings
				int[][] orderings = new int[P][N+1];
				int[][] temp_orderings = new int[P][N+1];
				for(int i = 0;i<P;i++)
				{
					one_ordering = ShuffleOrderings(N);
					for(int j = 0;j<N;j++)
					{
						orderings[i][j] = one_ordering[j];
					}
				}
				for(int i = 1;i<=Gen+1;i++)
				{
					
					orderings = calculateFitnessFunction(orderings,initial_similarity,P);   //calculate fitness cost
					orderings = sortOrderings(orderings,P);  //sort fitness cost
					newOrderings = orderingsSelection(orderings); //selection
					if(i==H+1)
					{
						tg.setH_Ordering(newOrderings); // store H ordering
						//generate matrix with H ordering
						for(int t = 0;t<N;t++)
						{
							ord_row[t] = (newOrderings[0][t])-1;
							ord_column[t] = (newOrderings[0][t])-1;
						}	
						for(int q = 0;q<H_similarity.length;q++)
						{
							for(int k = 0;k<H_similarity.length;k++)
							{
								for(int l = 0;l<H_similarity.length;l++)
								{
									H_similarity[k][l] = initial_similarity[ord_row[k]][ord_column[l]];
								}
							}
						}
						tg.setSimilarity_H(H_similarity);  // store H generation
					}
					if(i==Gen+1)
					{
						tg.setVeryLast_Ordering(newOrderings);// store last ordering
						//generate matrix with last ordering
						for(int z = 0;z<N;z++)
						{
							ord_row[z] = (newOrderings[0][z])-1;
							ord_column[z] = (newOrderings[0][z])-1;
						}	
						for(int x = 0;x<H_similarity.length;x++)
						{
							for(int c = 0;c<H_similarity.length;c++)
							{
								for(int v = 0;v<H_similarity[0].length;v++)
								{
									Gen_similarity[c][v] = initial_similarity[ord_row[c]][ord_column[v]];
								}
							}
						}
						tg.setSimilarity_Gen(Gen_similarity); // store last generation
						break;
					}
					Gen_Orderings = techniqueForGA(newOrderings,Cr,Mu);  //using different techniques for the new generation (list)
					orderings = convert_Gen_Orderings(P,N+1,Gen_Orderings); //convert orderings from list to int[] and iterate generation.
					
					
				}
				return tg;
				
	}
	public static int[][] calculateFitnessFunction(int[][] orderings,int[][] similarity,int P)
	{
		int fitnessCost = 0;
		for(int i = 0;i<P;i++)
		{
			fitnessCost = 0;
			for(int a = 0;a<similarity[0].length;a++)
			{
				for(int b = 0;b<similarity[0].length;b++)
				{
					fitnessCost+=similarity[orderings[i][a]-1][orderings[i][b]-1]*Math.abs(a-b);
				}
			}
			orderings[i][similarity.length] = fitnessCost;
		}
		return orderings;
	}
	public static List<int []> crossover(int[] array_1,int[] array_2,int cp,int length)
	{
		List<int []>result = new ArrayList<>();
		int[] repeat_index_1 = new int[length];
		int[] repeat_index_2 = new int[length];
		int[] part1 = new int[length];
		int[] part2 = new int[length];
		int[] part1_1 = new int[cp];
		int[] part1_2 = new int[length-cp];
		int[] part2_1 = new int[cp];
		int[] part2_2 = new int[length-cp];
		int part1_1_index = 0;
		int part1_2_index = 0;
		int part2_1_index = 0;
		int part2_2_index = 0;
		
		for(int i = 0;i<length;i++)  //initialize repeat_index for comparison
		{
			repeat_index_1[i] = -1;
			repeat_index_2[i] = -1;
		}
		for(int i = 0;i<length;i++)  //separate the each ordering into two parts
		{
			if(i<=cp-1)
			{
				part1_1[part1_1_index] = array_1[i];
				part2_1[part2_1_index] = array_2[i];
				part1_1_index++;
				part2_1_index++;
			}
			if(i>cp-1)
			{
				part1_2[part1_2_index] = array_1[i];
				part2_2[part2_2_index] = array_2[i];
				part1_2_index++;
				part2_2_index++;
			}
		}
		int a = 0;
		int b = 0;
		for(int i = 0;i<length;i++)	//combine different fragments together for each new part.
		{
			if(i<=cp-1)
			{
				part1[i] = part1_1[b];
				part2[i] = part2_1[b];
				b++;
			}
			if(i>cp-1)
			{
				part1[i] = part2_2[a];
	            part2[i] = part1_2[a];
	            a++;
			}
			
		}
		int index = 0;
		int index_2 = 0;
		for(int i = 0;i<length;i++) //get the index of repeat value in each ordering.
		{
			for(int j = i+1;j<length-1;j++)
			{
				if(part1[i]==part1[j])
				{
					repeat_index_1[index] = i;
					index++;
				}
				if(part2[i] == part2[j])
				{
					repeat_index_2[index_2] = i;
					index_2++;
				}
			}
			
		}
		for(int i = 0;i<length/2;i++) //change the repeated value
		{
			if(repeat_index_1[i]!=-1&&repeat_index_2[i]!=-1)
			{
				int temp;
				temp = part1[repeat_index_1[i]];
				part1[repeat_index_1[i]] = part2[repeat_index_2[i]];
				part2[repeat_index_2[i]] = temp;
			}
			else
			{
				continue;
			}
			
		}
		result.add(part1);
		result.add(part2);
		return result;
	}
	public static int generateRandomNumber(int min,int max)
	{
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	public static List<int[]> techniqueForGA(int[][] ordering,int Cr,int Mu)
	{
		int[][] Gen_Ordering_temp = new int[ordering.length][ordering[0].length];//add marks of used and unused.
		int[] Gen_Ordering_temp_2 = new int[ordering[0].length-1]; //result for mutation
		int[] Gen_Ordering_temp_3 = new int[ordering[0].length-1]; //one array for reproduction
		List<int[]> Gen_Ordering_final = new ArrayList<>();
		int tech_num = 0;
		for(int i = 0;i<ordering.length;i++)  // initialize ordering
		{
			for(int j = 0;j<ordering[0].length;j++)
			{
				if(j == ordering[0].length-1)
				{
					Gen_Ordering_temp[i][j] = -1; // -1 means unused. -2 means used.
					continue;
				}
				Gen_Ordering_temp[i][j] = ordering[i][j];
			}
		}
		for(;;)
		{
			if(Gen_Ordering_final.size()==ordering.length)  // when generation  is full
			{
				break;
			}
			tech_num = generateRandomNumber(1,100);
			if(tech_num>=1&&tech_num<=Cr)  //Crossover
			{
				int random_1;
				int random_2;
				int cp;
				int[] array_1 = new int[ordering[0].length-1];
				int[] array_2 = new int[ordering[0].length-1];
				int check_num = 0;
				for(int n = 0;n<ordering.length;n++) 
				{
					if(Gen_Ordering_temp[n][ordering[0].length-1]==-1)
					{
						check_num++;
					}
				}
				if(check_num<2) //check if the previous generation have not enough orderings
				{
					continue;
				}
				for(;;) //pick two different orderings randomly
				{
					random_1 = generateRandomNumber(1,ordering.length);
					random_2 = generateRandomNumber(1,ordering.length);
					if(random_1==random_2)
					{
						continue;
					}
					else if(Gen_Ordering_temp[random_1-1][ordering[0].length-1]==-2||Gen_Ordering_temp[random_2-1][ordering[0].length-1]==-2)
					{
						continue;
					}
					else
					{
						Gen_Ordering_temp[random_1-1][ordering[0].length-1] = -2;  //mark the ordering as used
						Gen_Ordering_temp[random_2-1][ordering[0].length-1] = -2;
						break;
					}
				}
				cp = generateRandomNumber(1,ordering[0].length-3); //generate crossover point
				for(int k = 0;k<ordering[0].length-1;k++)// initialize two array for crossover
				{
					array_1[k] = Gen_Ordering_temp[random_1-1][k];
					array_2[k] = Gen_Ordering_temp[random_2-1][k];
				}
				Gen_Ordering_final.add(crossover(array_1,array_2,cp,ordering[0].length-1).get(0));
				Gen_Ordering_final.add(crossover(array_1,array_2,cp,ordering[0].length-1).get(1));
			}
			else if(tech_num>=Cr+1&&tech_num<=Cr+Mu) //Mutation
			{
				int random_1;  //number for mutation
				int random_2;  // number for mutation
				int random_3;  //pick one ordering randomly
				int check_num = 0;
				for(int n = 0;n<ordering.length;n++)
				{
					if(Gen_Ordering_temp[n][ordering[0].length-1]==-1)
					{
						check_num++;
					}
				}
				if(check_num==0) //check if the previous generation have not enough orderings
				{
					break;
				}
				for(;;)
				{
					random_3 = generateRandomNumber(1,ordering.length);
					if(Gen_Ordering_temp[random_3-1][ordering[0].length-1]==-2)
					{
						continue;
					}
					else
					{
						Gen_Ordering_temp[random_3-1][ordering[0].length-1]=-2;
						break;
					}
					
				}
				for(;;) //pick two index randomly
				{
					random_1 = generateRandomNumber(1,ordering[0].length-2);
					random_2 = generateRandomNumber(1,ordering[0].length-2);
					if(random_1==random_2)
					{
						continue;
					}
					else
					{
						break;
					}
				}
				// start mutation
				int temp;
				temp = Gen_Ordering_temp[random_3-1][random_1-1];
				Gen_Ordering_temp[random_3-1][random_1-1] = Gen_Ordering_temp[random_3-1][random_2-1];
				Gen_Ordering_temp[random_3-1][random_2-1] = temp;
				for(int k = 0;k<ordering[0].length-1;k++)
				{
					Gen_Ordering_temp_2[k] = Gen_Ordering_temp[random_3-1][k];
				}
				Gen_Ordering_final.add(Gen_Ordering_temp_2);
				
			}
			else  //Reproduction
			{
				int random_1;
				int check_num = 0;
				for(int n = 0;n<ordering.length;n++)
				{
					if(Gen_Ordering_temp[n][ordering[0].length-1]==-1)
					{
						check_num++;
					}
				}
				if(check_num==0) //check if the previous generation have not enough orderings
				{
					break;
				}
				for(;;)
				{
					random_1 = generateRandomNumber(1,ordering.length);
					 if(Gen_Ordering_temp[random_1-1][ordering[0].length-1]==-2)
					{
						continue;
					}
					else
					{
						Gen_Ordering_temp[random_1-1][ordering[0].length-1]=-2; //mark the ordering as used
						break;
					}
				}
				for(int h = 0;h<ordering[0].length-1;h++)
				{
					Gen_Ordering_temp_3[h]=Gen_Ordering_temp[random_1-1][h];
				}
				Gen_Ordering_final.add(Gen_Ordering_temp_3); // add the ordering into new generation
			}
		}
		return Gen_Ordering_final; // return new generation
	}
	public static int[][] orderingsSelection(int[][] orderings)
	{
		int range = orderings.length/3; //divide into three parts
		int[][] newOrderings = new int[orderings.length][orderings[0].length];
		for(int i = 0;i<orderings.length-range;i++)
		{
			for(int j = 0; j<orderings[0].length;j++)
			{
				newOrderings[i][j] = orderings[i][j];
			}
		}
		int t = 0;
		for(int i = orderings.length-range;i<orderings.length;i++)
		{
			
			for(int j = 0; j<orderings[0].length;j++)
			{
				newOrderings[i][j] = newOrderings[t][j];
			}
			t++;
		}
		return newOrderings;
	}
	public static int[][] sortOrderings(int[][] orderings,int P)
	{
		List<int[]> OrderingList = new ArrayList<int[]>(P);
		int[] tempArray;
				for (int i = 0; i < P; i++) 
		        {
					tempArray = new int[orderings[0].length];
					for(int a = 0;a<orderings[0].length;a++)
					{
						tempArray[a] = orderings[i][a]; //put one ordering into tempArray 
					}
					OrderingList.add(tempArray);
		        }
				for (int i = 0; i < P; i++)  
	            {
	                for (int j = 0; j < P - 1 - i; j++) 
	                {
	                    if (OrderingList.get(j)[orderings[0].length-1] > OrderingList.get(j+1)[orderings[0].length-1]) 
	                    {
	                        int[] itemp;
	                        itemp = OrderingList.get(j); 
	                     
	                        OrderingList.set(j, OrderingList.get(j+1));
	                        
	                        OrderingList.set(j+1, itemp);
	                    }
	                }
	            }
				//update Orderings
				for(int i = 0;i<P;i++)
				{
					for(int j = 0;j<orderings[0].length;j++)
					{
						orderings[i][j] = OrderingList.get(i)[j];
					}
				}
				return orderings;
	}
	public static double caculateValueofFactorial(double N2)
	{  
        int count = 1;
        double i = 1;
        double factorialnumber = 0;
        while (count <= N2) {       
            i *= count;  
            factorialnumber += i;   
            count++;
        }
        return factorialnumber;
	}
	public static int initializeTheSizeofPopulation(int row)
	{
		
		String test = JOptionPane.showInputDialog(null, "please input the size of population (An integer and positive value):");
		if(test == null)
		{
			System.exit(0);
		}
		if(test.equals(""))
		{
			JOptionPane.showMessageDialog(null, "the value should not be empty", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheSizeofPopulation(row);
		}
		try
		{
		int P = Integer.parseInt(test);
		double F = caculateValueofFactorial(row);
		if( P > 0 && P<=F)
		{
			return P;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The size must be positive and less than or equal to the factorial number of the matrix ", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheSizeofPopulation(row);
		}
		} catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "The format of the value must be integer", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheSizeofPopulation(row);
		}
		
	}
	public static int initializeTheNumberofGenerations()
	{
		String test = JOptionPane.showInputDialog(null, "please input the number of Generations (An integer and positive value):");
		if(test == null)
		{
			System.exit(0);
		}
		if(test.equals(""))
		{
			JOptionPane.showMessageDialog(null, "the value should not be empty", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheNumberofGenerations();
		}
		try
		{
		int Gen = Integer.parseInt(test);
		if(Gen > 0)
		{
			return Gen;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The size must be an integer and positive ", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheNumberofGenerations();
		}
		}catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "The format of the value must be integer", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheNumberofGenerations();
		}
		
	}
	public static int initializeTheProbabilityForCrossover()
	{
		String test = JOptionPane.showInputDialog(null, "please input the probability of crossover (An integer and positive value):");
		if(test == null)
		{
			System.exit(0);
		}
		if(test.equals(""))
		{
			JOptionPane.showMessageDialog(null, "the value should not be empty", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheProbabilityForCrossover();
		}
		
		try
		{
		int Cr = Integer.parseInt(test);
		if( Cr >= 1&& Cr<=100)
		{
			return Cr;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The value must be an integer and range from 1 to 100 ", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheProbabilityForCrossover();
		}
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "The format of the value must be integer", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheProbabilityForCrossover();
		}
		
		
	}
	public static int initializeTheProbabilityForMutation(int Cr)
	{
		String test = JOptionPane.showInputDialog(null, "please input the probability of mutation (An integer, positive value and the sum of the value of mutation and crossover must be less than or equal to 100 ");
		if(test == null)
		{
			System.exit(0);
		}
		if(test.equals(""))
		{
			JOptionPane.showMessageDialog(null, "the value should not be empty", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheProbabilityForMutation(Cr);
		}
		
		try
		{
		int Mu = Integer.parseInt(test);
		if( Mu >= 1&& (Mu+Cr)<=100)
		{
			return Mu;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The value must be an integer, the sum of mutation and crossover must be less than or equal to 100 and the range of mutation is from 1 to 100 )", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheProbabilityForMutation(Cr);
		}
		}catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "The format of the value must be integer", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return initializeTheProbabilityForMutation(Cr);
		}
		
	}
	public static int definedGenerationNumber(int Gen)
	{
		String test = JOptionPane.showInputDialog(null, "please define generation number (An integer and less than the number of generation,your the number of generation is "+Gen+":");
		if(test == null)
		{
			System.exit(0);
		}
		if(test.equals(""))
		{
			JOptionPane.showMessageDialog(null, "the value should not be empty", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return definedGenerationNumber(Gen);
		}
		
		try
		{
		int gen = Integer.parseInt(test);
		
		if( gen > 0&& gen<=Gen)
		{
			return gen;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The value must be an integer and less than Gen ", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return definedGenerationNumber(Gen);
		}
		}catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "The format of the value must be integer", "ErrorMessage ", JOptionPane.ERROR_MESSAGE);
			return definedGenerationNumber(Gen);
		}
	}
	//Initialize - End
	//Generate ordering
	public static int[] ShuffleOrderings(int maxnum)
	{
        List<Integer> li = new ArrayList<Integer>();
		int[] result = new int[maxnum];
		int[] test_array = new int[maxnum];
		int check_num = 0;
		for(int i=1;i <= maxnum;i++){
			li.add(i);
			test_array[i-1] = i; 
		}
		
		for(int num=0;;num++)
		{
			check_num = 0;
			if(num>0)
			{
				for(int m = 0;m<maxnum;m++)
				{
					test_array[m] = result[m];
				}
			}
			Collections.shuffle(li);
			for(int j = 0; j< li.size();j++)
			{
				result[j] = li.get(j);
				
			}
			for(int k = 0;k<maxnum;k++)
			{
				if(test_array[k]==result[k])
				{
					check_num++;
				}
			}
			if(check_num>=maxnum)
			{
				continue;
			}
			else
			{
				break;
			}
		}
		return result;
	}
	public static boolean checkDiagonalLine(int[][] result,int row)
	{
		 int check_num = 0;
	        for(int i = 0;i<row;i++)
	        {
	        	for(int j = 0;j<row;j++)
	        	{
	        		if(i==j)
	        		{
	        			if(result[i][j]!=0)
	        			{
	        				check_num++;
	        			}
	        		}
	        	}
	        }
	        if(check_num!=0)
	        {
				return false;
	        }
	        else
	        {
	        	return true;
	        }
	}
	public static int[][] caculateAverageNumberForDiagonalLine(int[][] similarity,int row)
	{
		int check_nonzero;
		int sum_nonzero;
		for(int a = 0;a<row;a++)
        {
        	check_nonzero = 0;
        	sum_nonzero = 0;
        	for(int b = 0;b<row;b++)
        	{
        		if(similarity[a][b]!=0)
        		{
        			check_nonzero++;
        			sum_nonzero+=similarity[a][b];
        		}
        	}
        	similarity[a][a] = (sum_nonzero)/(check_nonzero);
        	
        }
		return similarity;
	}
	//Generate Heap map
	public static void TableCreation(int[][] sim, int v, int[] ord) {

		final Object rowData[][] = new Object[v][v];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				rowData[i][j] = sim[i][j];
			}
		}
		final String columnNames[] = new String[v];
		final String rowNames[] = new String[v];
		for (int i = 0; i < v; i++) {
			columnNames[i] = ord[i] + "";
		}
		
		final JTable table = new JTable(rowData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setDefaultRenderer(Object.class, new EvenOddRenderer());
		JFrame frame = new JFrame("Heat Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(50*v, 50*v);
		frame.setVisible(true);
	}
	
	
}

/*result = new String[N];
for(int i = 0;i<N;i++)
{
	//clean the "null" in the array
    result[i] = "null";
	result[i] = "";
	for(int j = 0;j<row;j++)
	{
		if(test_result[j]<10)
		{
			result[i]+=" ";
		}
		result[i] += test_result[j]+""+" ";
	}
	test_result = ShuffleOrderings(row);
}   
//add orderings into AI17.txt and output 
FileWriter fw = new FileWriter("src/AI17.txt");
for (int k = 0; k < P; k++) {
	fw.write(result[k]+"\r\n");
}
fw.close();
//open the AI17.txt
try { 
     Runtime.getRuntime().exec(
    "cmd.exe  /c notepad src/AI17.txt");
} catch (Exception e) {
e.printStackTrace();
}*/

/*
//get new index of similarity matrix
for(int i = 0;i<similarity.length;i++)
{
	ord_row[i] = (orderings[0][i])-1;
	ord_column[i] =(orderings[0][i])-1;
}
//generate new similarity matrix
for(int i = 0;i<similarity.length;i++)
{
	for(int j = 0;j<similarity.length;j++)
	{
		similarity[i][j] = similarity[ord_row[i]][ord_column[j]];
	}
}
//calculate fitness cost
for(int i = 0;i<similarity.length;i++)
{
	for(int j = 0;j<similarity.length;j++)
	{
		fitnessCost += similarity[i][j]*Math.abs(i-j);
	}
}
orderings[0][similarity.length] = fitnessCost;
return orderings;
*/
