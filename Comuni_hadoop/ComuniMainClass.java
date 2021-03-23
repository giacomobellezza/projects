/*     */ package bigdataman.belal.comuni;
/*     */ 
/*     */ import com.mongodb.MongoClient;
/*     */ import com.mongodb.client.MongoCollection;
/*     */ import com.mongodb.client.MongoDatabase;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.hadoop.util.ToolRunner;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.bson.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComuniMainClass
/*     */ {
/*     */   private static MongoClient client;
/*     */   private static MongoDatabase database;
/*     */   private static MongoCollection<Document> collection;
/*     */   
/*     */   private static void initSystem()
/*     */   {
/*  38 */     LogManager.getLogger("org.mongodb.driver.connection").setLevel(Level.ERROR);
/*  39 */     LogManager.getLogger("org.mongodb.driver.management").setLevel(Level.ERROR);
/*  40 */     LogManager.getLogger("org.mongodb.driver.cluster").setLevel(Level.ERROR);
/*  41 */     LogManager.getLogger("org.mongodb.driver.protocol.insert").setLevel(Level.ERROR);
/*  42 */     LogManager.getLogger("org.mongodb.driver.protocol.query").setLevel(Level.ERROR);
/*  43 */     LogManager.getLogger("org.mongodb.driver.protocol.update").setLevel(Level.ERROR);
/*     */     try {
/*  45 */       client = new MongoClient("localhost", 27017);
/*  46 */       database = client.getDatabase("comuni");
/*  47 */       collection = database.getCollection("comuni");
/*     */     } catch (Exception e) {
/*  49 */       e.printStackTrace(System.out);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  56 */     if (args.length != 1) {
/*  57 */       throw new IllegalArgumentException("Inserisci un'operazione");
/*     */     }
/*  59 */     initSystem();
/*  60 */     BufferedReader in = null;
/*     */     try {
/*  62 */       in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
/*     */     } catch (IOException localIOException) {}
/*  64 */     String str = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  69 */     if (args[0].equals("aggregate")) {
/*  70 */       System.out.println("   ---------------------  \n\r   AGGREGAZIONE MapReduce\n\r   ---------------------\n\rInserisci: <aggregation_term>\n\roppure: <key_condition> <value_condition> <aggregation_term>\n\r");
/*     */       try
/*     */       {
/*  73 */         str = in.readLine(); } catch (IOException localIOException1) {}
/*  74 */       String[] tmp = str.split(" ");
/*     */       
/*  76 */       String s = "";
/*  77 */       String collectionName = "";
/*  78 */       if (tmp.length == 1) {
/*  79 */         s = tmp[0];
/*  80 */         collectionName = s;
/*     */       } else {
/*  82 */         s = tmp[0] + "," + tmp[1] + "," + tmp[2];
/*  83 */         collectionName = tmp[2] + "_in_" + tmp[0] + "_" + tmp[1];
/*     */       }
/*  85 */       System.out.println("  -- Operazione in corso ...");
/*     */       try {
/*  87 */         ToolRunner.run(new MapReduceTool(s, collectionName), args);
/*     */       }
/*     */       catch (Exception localException) {}
/*  90 */       MongoCollection<Document> outputCollection = database.getCollection("aggregation_" + collectionName);
/*  91 */       IOOperations.outputAggregate(outputCollection, "aggregation_" + collectionName);
/*     */       
/*  93 */       IOOperations.updatelog("AGGREGATE", tmp);
/*     */ 
/*     */ 
/*     */     }
/*  97 */     else if (args[0].equals("find")) {
/*  98 */       System.out.println("   ---------------------  \n\r   RICERCA Elemento\n\r   ---------------------  \n\rInserisci: <key> <value>\n\r");
/*     */       try {
/* 100 */         str = in.readLine(); } catch (IOException localIOException2) {}
/* 101 */       String[] tmp = str.split(" ");
/* 102 */       System.out.println("   ---------------------  \n\r");
/*     */       try
/*     */       {
/* 105 */         String el = tmp[1];
/* 106 */         for (int i = 2; i < tmp.length; i++)
/* 107 */           el = el + " " + tmp[i];
/* 108 */         Finder.findElementByKey(collection, tmp[0], el);
/* 109 */       } catch (ArrayIndexOutOfBoundsException E) { System.out.println("Formato input non valido");
/*     */       }
/* 111 */       IOOperations.updatelog("FIND", tmp);
/*     */ 
/*     */ 
/*     */     }
/* 115 */     else if (args[0].equals("help")) {
/* 116 */       IOOperations.helpCommand();
/*     */ 
/*     */ 
/*     */     }
/* 120 */     else if (args[0].equals("log")) {
/* 121 */       IOOperations.getLog();
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 126 */       System.out.println("Comando errato, digita \n\r\n\r    sh run.sh help \n\r\n\rper avere informazioni");
/*     */     }
/* 128 */     System.out.println();
/* 129 */     System.out.println("   ---------------------  \n\r   COMPLETATO\n\r   ---------------------  ");
/*     */   }
/*     */ }


/* Location:              C:\Users\Federico\Downloads\Comuni-Italia-Project\Comuni-Italia-Project\comuni.jar!\bigdataman\belal\comuni\ComuniMainClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */