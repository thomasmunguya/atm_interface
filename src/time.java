
/* @author Mwansa Gee Phiri*/
public class time {
            
    public int hour ;
    public int minute;
    public int seconds;
    
    public time()
    {
        int sec = System.currentTimeMillis();
        int TS = sec/1000;
        int TM = TS/60;
        int TH = TM/60; 
        
      seconds = TS % 60;
      hour = TH %60 ; 
      minute = TM %60; 
    } 
    
}
