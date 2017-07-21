/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author vgaur
 */
public class Antop {

    int size;
    int ant;
    int dst;
    Double dist[][];
    Antop() throws FileNotFoundException, IOException
    {
        
        FileInputStream f=new FileInputStream("C:\\Users\\vgaur\\Desktop\\abc3.txt");
        BufferedReader br=new BufferedReader(new InputStreamReader(f));
        String r;
        
        size=Integer.parseInt(br.readLine());
        ant=100;
        dist=new Double[size][size];
        dst=Integer.parseInt(br.readLine());
        
        Scanner obj=new Scanner(System.in);
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                r=br.readLine();
                int x=Integer.parseInt(r);
                System.out.println(x);
                dist[i][j]=x*1.0;
            }
        }
        
        antColony c=new antColony(size, dist, ant, dst);
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Antop t=new Antop();
        // TODO code application logic here
    }
    
}
