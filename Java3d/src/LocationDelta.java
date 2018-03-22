import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class LocationDelta {
	private String room;
	private int numOfConnections;
	ArrayList<WifiConnection> connections;
	private int floor;
	private int sector;

	LocationDelta (int a) throws IOException{
		this.numOfConnections=a;
		this.floor=-1;
		setLocation();
		calculateLocation();
	}
	
	public int getFloor(){
		return this.floor;
	}
	public int getSector(){
		return this.sector;
	}
	public void setLocation() throws IOException {
        URL readFromDB = new URL("http://locaware.esy.es/SSIDoutput.php");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(readFromDB.openStream()));
        
        String inputLine;
        connections=new ArrayList<WifiConnection>();
        while ((inputLine = in.readLine()) != null){
            String[] values = inputLine.split("<br>");             
            for (int i=0;i<this.numOfConnections;i++){
            	connections.add(new WifiConnection(values[3*i],values[3*i+1], (int) Integer.parseInt(values[3*i+2])));          	
            }
        }
        in.close();    	
        
    }
    public int getNumberOfConections(){
    	return this.numOfConnections;
    }
    public void calculateLocation(){
    	Collections.sort(this.connections, new Comparator<WifiConnection>() {
    	    @Override
    	    public int compare(WifiConnection w1, WifiConnection w2) {
    	        if (w1.getrssi() < w2.getrssi())
    	            return 1;
    	        if (w1.getrssi() > w2.getrssi())
    	            return -1;
    	        return 0;
    	    }
    	});
    	System.out.println(this.connections.get(0).getbssid());

    	while (true){
    		if (this.connections.get(0).getbssid().equals("90:84:0d:d9:87:af")) {
    			floor=9;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("96:84:0d:d9:87:af")){
    			floor=9;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("e8:94:f6:d0:25:34")){
    			floor=8;
    			break;
    		}
    		else if (this.connections.get(1).getbssid().equals("e8:94:f6:d0:25:34")){
    			floor=8;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("0a:ee:0c:70:2d:a5")){
    			floor=7;
    			break;
    		}
    		else if (this.connections.get(1).getbssid().equals("0a:ee:0c:70:2d:a5")){
    			floor=7;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("e8:de:27:90:3a:5a")){
    			floor=6;
    			break;
    		}
    		else if (this.connections.get(1).getbssid().equals("e8:de:27:90:3a:5a")){
    			floor=6;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("2a:a4:3c:a3:ec:99")){
    			floor=5;
    			sector=1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("18:33:9d:d3:fa:60")){
    			floor=5;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("18:33:9d:d3:fa:62")){
    			floor=5;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("2a:a4:3c:a3:ed:fa")){
    			floor=5;
    			sector=-1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("00:16:0a:0f:7e:d2")){
    			floor=5;
    			sector=-1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("00:18:39:d4:8a:8a")){
    			floor=4;
    			sector=-1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("2a:a4:3c:a3:ed:fa")){
    			floor=4;
    			sector=-1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("94:44:52:8b:a1:43")){
    			floor=4;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("64:70:02:57:9f:34")){
    			floor=4;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("84:c9:b2:d4:92:a4")){
    			floor=4;
    			sector=1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("c8:be:19:ae:82:b4")){
    			floor=4;
    			sector=1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("c8:be:19:b8:9f:7c")){
    			if (this.connections.get(1).getbssid().equals("32:cd:a7:1d:09:2d")){
        			floor=3;
        			break;
        		}
    			else if (this.connections.get(0).getrssi()>-66){
    				floor=3;
    				break;
    			}
    			else if (this.connections.get(0).getrssi()<-72){
    				floor=2;
    			}
    			else if (this.connections.get(1).getbssid().equals("c8:3a:35:5d:25:18")){
        			floor=2;
        			break;
        		}
    			else {
    				break;
    			}
    		}
    		else if (this.connections.get(0).getbssid().equals("00:22:6b:4d:55:8b")){
    			floor=1;
    			break;
    		}
    		else if (this.connections.get(1).getbssid().equals("00:22:6b:4d:55:8b")){
    			floor=1;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("00:1c:f9:c1:0c:70")){
    			floor=0;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("00:1c:f9:2e:a4:51")){
    			floor=0;
    			break;
    		}
    		else if (this.connections.get(0).getbssid().equals("00:1c:f9:2e:a4:52")){
    			floor=0;
    			break;
    		}
    		else {
    			break;
    		}
    	}
    	System.out.println(floor);
    }
    public static void main(String[] args) throws IOException, InterruptedException{
    	LocationDelta loc = new LocationDelta(2);
    	//loc.setLocation();
    	while (true){
	    	loc.setLocation();
	    	loc.calculateLocation();
	    	TimeUnit.SECONDS.sleep(3);
    	}
    }
}