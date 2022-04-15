import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;

public class BaseballElimination
{
    // create a baseball division from given filename in format specified below
    private final HashMap<String, Integer> teams;
    private final int numTeam;
    private final int[] win;
    private final int[] loss;
    private final int[] remain;
    private final int[][] game;
    
    public BaseballElimination(String filename)                   
    {
        if (filename == null) throw new IllegalArgumentException();

        In in = new In(filename);
        
        numTeam = in.readInt();
        teams = new HashMap<String, Integer>();
        win = new int[numTeam]; 
        loss = new int[numTeam]; 
        remain = new int[numTeam];
        game = new int[numTeam][numTeam];

        for (int i = 0; i < numTeam; ++i)
        {
            teams.put(in.readString(), i); 
            win[i] = in.readInt();
            loss[i] = in.readInt();
            remain[i] = in.readInt(); 

            for (int j = 0; j < numTeam; ++j) game[i][j] = in.readInt();
        }
    }
    public Iterable<String> teams()
    {
        return teams.keySet();
    }
    public int numberOfTeams()
    {
       return numTeam; 
    }                        
    // number of wins for given team
    public int wins(String team)
    {
        checkTeam(team);
        return win[teams.get(team)];
    } 
    // number of losses for given team
    public int losses(String team)
    {
        checkTeam(team);
        return loss[teams.get(team)];
    }
    // number of remaining games for given team
    public int remaining(String team)
    {
        checkTeam(team);
        return remain[teams.get(team)];
    }
    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        checkTeam(team1);
        checkTeam(team2);
        return game[teams.get(team1)][teams.get(team2)];
    }
    private FordFulkerson buildFordFulkerson(String team)
    {
        checkTeam(team);

        int numNodes = (numTeam - 1) * numTeam / 2 + 2;
        int evalTeam = teams.get(team);
        int s = evalTeam; // put s to the evaluate team position
        int t = numNodes - 1;

        FlowNetwork fNet = new FlowNetwork(numNodes);

        int idx = numTeam;
        for (int i = 0; i < numTeam; i++)
        {
            if (i == evalTeam) continue;
            // from teams to t
            if (win[evalTeam] + remain[evalTeam] - win[i] < 0) return null;
            fNet.addEdge(new FlowEdge(i, t, win[evalTeam] + remain[evalTeam] - win[i]));
            for (int j = i + 1; j < numTeam; j++)
            {
                if (j == evalTeam) continue;
                int curIdx = idx++;
                // from s to games
                fNet.addEdge(new FlowEdge(s, curIdx, game[i][j]));
                // from games to teams
                fNet.addEdge(new FlowEdge(curIdx, i, Double.POSITIVE_INFINITY));
                fNet.addEdge(new FlowEdge(curIdx, j, Double.POSITIVE_INFINITY));
            } 
        }
        return new FordFulkerson(fNet, s, t);
    }
    // is given team eliminated?   
    public boolean isEliminated(String team)
    {
        checkTeam(team);
        
        int outCapacity = 0;
        int evalTeam = teams.get(team);
        for (int i = 0; i < numTeam; i++)
        {
            if (i == evalTeam) continue;
            if (win[evalTeam] + remain[evalTeam] - win[i] < 0) return true;

            for (int j = i + 1; j < numTeam; j++)
            {
                if (j == evalTeam) continue;
                outCapacity += game[i][j];
            }
        }

        FordFulkerson ff = this.buildFordFulkerson(team);
        if (outCapacity == ff.value()) return false;
        else return true;
    }
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team)
    {
        checkTeam(team);
        if (!isEliminated(team)) return null;
        int evalTeam = teams.get(team);
        LinkedList<String> r = new LinkedList<String>();
        FordFulkerson ff = buildFordFulkerson(team);
        
        for (String t : teams.keySet()) 
        {
            int teamID = teams.get(t);
            if (teamID == evalTeam) continue;
            if (ff == null)
            { if (win[evalTeam] + remain[evalTeam] - win[teamID] < 0) r.add(t); } 
            else 
            { if (ff.inCut(teamID)) r.add(t); } 
        }  
        return r;
    }
    private void checkTeam(String team)
    {
        if (team == null) throw new IllegalArgumentException();
        if (!teams.containsKey(team)) throw new IllegalArgumentException();
    }      
    public static void main(String[] args)
    {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams())
        {
            if (division.isEliminated(team))
            {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            }
            else 
                StdOut.println(team + " is not eliminated");
        }
    }
}