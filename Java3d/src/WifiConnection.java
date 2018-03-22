
public class WifiConnection {
      private String ssid;
      private int rssi;
      private String bssid;
      
      WifiConnection(String a, String b, int c){
    	  this.ssid=a;
    	  this.rssi=c;
    	  this.bssid=b;
      }
      
      public String getssid(){
    	  return this.ssid;
      }
      
      public String getbssid(){
    	  return this.bssid;
      }
      
      public int getrssi(){
    	  return this.rssi;			  
      }
      
      public void printData(){
    	  System.out.println(this.ssid);
    	  System.out.println(this.bssid);
    	  System.out.println(this.rssi);
      }
}
