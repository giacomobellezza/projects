/*    */ package bigdataman.belal.comuni;
/*    */ 
/*    */ import com.mongodb.BasicDBObjectBuilder;
/*    */ import com.mongodb.hadoop.io.BSONWritable;
/*    */ import java.io.IOException;
/*    */ import org.apache.hadoop.conf.Configuration;
/*    */ import org.apache.hadoop.io.IntWritable;
/*    */ import org.apache.hadoop.io.Text;
/*    */ import org.apache.hadoop.mapreduce.Reducer;
/*    */ import org.apache.hadoop.mapreduce.Reducer.Context;
/*    */ import org.bson.BSONObject;
/*    */ 
/*    */ public class ComuniReducer
/*    */   extends Reducer<Text, IntWritable, BSONWritable, IntWritable>
/*    */ {
/* 16 */   private BSONWritable reduceResult = new BSONWritable();
/*    */   
/*    */   public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, BSONWritable, IntWritable>.Context context)
/*    */     throws IOException, InterruptedException
/*    */   {
/* 21 */     Configuration conf = context.getConfiguration();
/* 22 */     String param = conf.get("param");
/* 23 */     String[] args = param.split(",");
/*    */     String s;
/* 25 */     String s; if (args.length == 3) {
/* 26 */       s = args[2];
/*    */     } else {
/* 28 */       s = args[0];
/*    */     }
/* 30 */     int sum = 0;
/* 31 */     for (IntWritable value : values) {
/* 32 */       sum += value.get();
/*    */     }
/* 34 */     BSONObject outDoc = BasicDBObjectBuilder.start().add(s, key.toString()).get();
/* 35 */     this.reduceResult.setDoc(outDoc);
/* 36 */     context.write(this.reduceResult, new IntWritable(sum));
/*    */   }
/*    */ }


/* Location:              C:\Users\Federico\Downloads\Comuni-Italia-Project\Comuni-Italia-Project\comuni.jar!\bigdataman\belal\comuni\ComuniReducer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */