/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antop;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 *
 * @author vgaur
 */
public class antColony {
    
    int size;
    int ant;
    Double rdist[][];  // Distance between every node
    Double trail[][];  // Amount of Pheromone on the edge
    Double delta[][];  // Change of pheromone trail on edge
    Double nears[][];  // (Edge Distance)^(-beta)
    int tour[];        // Vertices travelled by the current ant
    Double qual[][];   // (trail)^(-alpha)*nears
    Double blen;       // Best Length
    
    Double alpha;      // 0.2
    Double beta;       // 0.6
    Double evap;
    Double avglen;     // 0.6
    PrintStream console=null;
    int end;
    long start, stop;
    antColony(int size, Double dist[][], int ant, int dst) throws FileNotFoundException
    {
        console=new PrintStream("C:\\Users\\vgaur\\Desktop\\output.txt");
        System.setOut(console);
        start=System.currentTimeMillis();
        this.ant=ant;
        this.size=size;
        this.end=dst;
        rdist=new Double[size][size];
        trail=new Double[size][size];
        delta=new Double[size][size];
        qual=new Double[size][size];
        nears=new Double[size][size];
        tour=new int[size+1];
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                rdist[i][j]=dist[i][j];
                console.print(rdist[i][j]+"\t");
            }
            console.println();
        }
        alpha=0.2;
        beta=0.6;
        evap=0.6;
        init();
        console.println("Nears Value:");
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                console.print(nears[i][j]+"\t");           
            }
            console.println();
        }
        for(int i=0;i<2;i++)
        runAllAnts();
    }
    
    void init()
    {
        Double sum=0.0;
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                nears[i][j]=Math.pow(rdist[i][j], -beta);
                if(rdist[i][j]!=0)
                {
                    trail[i][j]=0.2;
                    sum+=rdist[i][j];
                }        
                else
                {
                    trail[i][j]=0.0;
                }
            }
        }
        avglen=sum/size;
        blen=Double.MAX_VALUE;
    }
    
    int find(Double sums[], int n, Double val)
    {
        int i;
        int l=--n;
        int x=0;
        for(i=0;i<n;i++)
        {
            if(i==n-1)
            {
                break;
            }
            else if(sums[0]>val)
            {
                break;
            }
            else if(sums[i]<val&&val<sums[i+1])
            {
                x=i;
            }
        }
        /*for(--n, i=0;i<n;)
        {
            int k=(n+i)>>1;
            if(sums[k]<val)
                i=k+1;
            else
                n=k;
        }*/
        return i;
    }
    
    void placePhero(int tour[], Double amount)
    {
        int src,dst;
        src=tour[0];
        for(int i=size;--i>=0;)
        {
            dst=src;
            src=tour[i];
            delta[src][dst]+=amount;
        }
    }
    Double runAnt()
    {
        Double sum;
        int src=0, dst=0;
        Random rand=null;
        src=0;//(int)((size-1)*Math.random());
        boolean visited[]=new boolean[size];
        for(int i=0;i<size;i++)
        {
            visited[i]=false;
        }
        visited[src]=true;
        tour[0]=src;
        int x=0;
        Double sums[]=new Double[size];
        int d[]=new int[size];
        Double len=0.0;
        int l=1;
        
        for(int j=0;j<size;j++)
        {
            Double pheromone=0.0;
            Double probability[]=new Double[size];
            
            for(int i=0;i<size;i++)
            {
                probability[i]=0.0;
            }
            for(int i=0;i<size;i++)
            {
                if(!visited[i])
                {
                    if(rdist[src][i]!=0)
                    {
                        if(i!=src)
                        {
                             pheromone+=qual[src][i];
                        }
                    }                 
                   
                }
            }
            console.println("Pheromone:"+pheromone);
            for(int i=0;i<size;i++)
            {
                if(i!=src)
                {
                    if(visited[i])
                    {
                        probability[i]=0.0;
                    }
                    else
                    {
                        Double numerator=0.0;
                        if(rdist[src][i]!=0)
                            numerator=qual[src][i];
                        console.println("Numerator:"+numerator);
                        probability[i]=numerator/pheromone;
                    }
                }
                
            }
            
            Double r=1.0*Math.random();
            Double total=0.0;
            
            for(int i=0;i<size;i++)
            {
                console.println("Probabiity:"+probability[i]);
            }
            console.println("Random:"+r);
            for(int i=0;i<size;i++)
            {
                total+=probability[i];
                console.println("total:"+total+"\t"+i);
                if(total>=r)
                {
                    src=i;
                    break;
                }
            }
            console.println("src:"+src);
            tour[++x]=src;
            if(src==end)
            {
                break;
            }
            visited[src]=true;
        }
        for(int i=0;i<x;i++)
        {
            len+=rdist[tour[i]][tour[i+1]];
        }
        
        console.println("Tour:");  
        for(int i=0;i<x+1;i++)
        {
            console.println(tour[i]+"\t");
        }
        
        console.println("Length:"+len);
        
        Double chg=avglen/len;
        placePhero(tour, chg);
        console.println("Delta values:");
        
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                console.print(delta[i][j]+"\t");
            }
            console.println();
        }
        return len;
    }
    
    void runAllAnts()
    {
        console.println("Quals Value:");
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                delta[i][j]=0.0;
                qual[i][j]=nears[i][j]*Math.pow(trail[i][j], alpha);
                console.print(qual[i][j]+"\t");
            }
            console.println();
        }
        
        for(int i=0;i<ant;i++)
        {
            console.println("Ant "+(i+1)+":\n");
            Double len=runAnt();
            if(len<blen)
            {
                blen=len;
            }
        }
        
        Double fact=1-evap;
        Double t;
        
        
        console.println("Trail value:");
        for(int i=size;--i>=0;)
        {
            for(int j=i;j>=0;j--)
            {
                t=fact*trail[i][j]+evap*(delta[i][j]+delta[j][i]);
                
                trail[i][j]=trail[j][i]=t;
                
            }
        }
        
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {                
                console.print(trail[i][j]+"\t");
            }
            console.println();
        }
        console.println("Best length:"+blen);
        stop=System.currentTimeMillis();
        console.println("Time taken:"+(stop-start));
        
    }
}
