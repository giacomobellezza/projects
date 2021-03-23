/*    */ package bigdataman.belal.comuni;
/*    */ 
/*    */ import com.mongodb.Block;
/*    */ import com.mongodb.client.FindIterable;
/*    */ import com.mongodb.client.MongoCollection;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.PrintStream;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Arrays;
/*    */ import java.util.Date;
/*    */ import org.bson.Document;
/*    */ 
/*    */ public class IOOperations
/*    */ {
/* 23 */   private static BufferedWriter outFile = null;
/* 24 */   private static BufferedWriter logFile = null;
/*    */   
/* 26 */   private static Block<Document> printBlock = new Block() {
/*    */     public void apply(Document document) {
/* 28 */       System.out.println(document.toJson());
/*    */       try {
/* 30 */         IOOperations.outFile.newLine();
/* 31 */         IOOperations.outFile.write(document.toJson());
/* 32 */         IOOperations.outFile.flush();
/* 33 */       } catch (IOException e) { System.out.println("problema in output");
/*    */       }
/*    */     }
/*    */   };
/*    */   
/*    */   public static void outputAggregate(MongoCollection<Document> outputCollection, String name) {
/*    */     try {
/* 40 */       outFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output/" + name + ".json", true), "UTF-8"));
/*    */     } catch (IOException e) {
/* 42 */       System.out.println("problema in output");
/*    */     }
/* 44 */     outputCollection.find().forEach(printBlock);
/*    */   }
/*    */   
/*    */   public static void helpCommand()
/*    */   {
/* 49 */     BufferedReader inFile = null;
/*    */     try
/*    */     {
/* 52 */       inFile = new BufferedReader(new InputStreamReader(new FileInputStream("input/help.txt")));
/* 53 */       String s; while ((s = inFile.readLine()) != null) { String s;
/* 54 */         System.out.println(s);
/*    */       }
/*    */     }
/*    */     catch (IOException localIOException) {}
/*    */   }
/*    */   
/*    */ 
/*    */   public static void updatelog(String operation, String[] param)
/*    */   {
/* 63 */     DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
/* 64 */     Date date = new Date();
/*    */     try
/*    */     {
/* 67 */       File log = new File("output/log.txt");
/* 68 */       boolean exists = true;
/* 69 */       if (!log.exists())
/* 70 */         exists = false;
/* 71 */       logFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output/log.txt", true), "UTF-8"));
/* 72 */       if (!exists)
/* 73 */         logFile.write(dateFormat.format(date) + "  *** LOG FILE --- DOCUMENT DATABASE COMUNI D'ITALIA ***\n" + dateFormat.format(date) + "  *** Hadoop  -- VERSIONE 2.7.4\n" + dateFormat.format(date) + "  *** MongoDB -- VERSIONE 3.4.10\n\r\n\r");
/* 74 */       logFile.newLine();
/* 75 */       logFile.write(dateFormat.format(date) + " operazione: " + operation + " -- parametri: " + Arrays.toString(param));
/* 76 */       logFile.flush();
/*    */     } catch (IOException e) {
/* 78 */       System.out.println("problema con file di log");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public static void getLog()
/*    */   {
/* 85 */     BufferedReader inFile = null;
/*    */     try
/*    */     {
/* 88 */       inFile = new BufferedReader(new InputStreamReader(new FileInputStream("output/log.txt")));
/* 89 */       String s; while ((s = inFile.readLine()) != null) { String s;
/* 90 */         System.out.println(s);
/*    */       }
/* 92 */     } catch (IOException e) { System.out.println("IMPOSSIBILE RECUPERARE IL FILE DI LOG");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Federico\Downloads\Comuni-Italia-Project\Comuni-Italia-Project\comuni.jar!\bigdataman\belal\comuni\IOOperations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */