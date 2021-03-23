/*    */ package bigdataman.belal.comuni;
/*    */ 
/*    */ import com.mongodb.Block;
/*    */ import com.mongodb.client.FindIterable;
/*    */ import com.mongodb.client.MongoCollection;
/*    */ import com.mongodb.client.model.Filters;
/*    */ import java.io.PrintStream;
/*    */ import org.bson.Document;
/*    */ 
/*    */ 
/*    */ public class Finder
/*    */ {
/* 13 */   private static Block<Document> printBlock = new Block() {
/*    */     public void apply(Document document) {
/* 15 */       System.out.println(document.toJson());
/*    */     }
/*    */   };
/*    */   
/*    */   public static void findElementByKey(MongoCollection<Document> collection, String key, String value) {
/* 20 */     collection.find(Filters.eq(key, value)).forEach(printBlock);
/*    */   }
/*    */ }


/* Location:              C:\Users\Federico\Downloads\Comuni-Italia-Project\Comuni-Italia-Project\comuni.jar!\bigdataman\belal\comuni\Finder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */