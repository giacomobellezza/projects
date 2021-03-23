/*    */ package bigdataman.belal.comuni;
/*    */ 
/*    */ import com.mongodb.BasicDBObject;
/*    */ import java.io.IOException;
/*    */ import org.apache.hadoop.conf.Configuration;
/*    */ import org.apache.hadoop.io.IntWritable;
/*    */ import org.apache.hadoop.io.Text;
/*    */ import org.apache.hadoop.mapreduce.Mapper;
/*    */ import org.apache.hadoop.mapreduce.Mapper.Context;
/*    */ import org.bson.BSONObject;
/*    */ 
/*    */ public class ComuniMapperNoCondition
/*    */   extends Mapper<Object, BSONObject, Text, IntWritable>
/*    */ {
/* 15 */   private final IntWritable one = new IntWritable(1);
/*    */   
/*    */   public void map(Object key, BSONObject val, Mapper<Object, BSONObject, Text, IntWritable>.Context context)
/*    */     throws IOException, InterruptedException
/*    */   {
/* 20 */     Configuration conf = context.getConfiguration();
/* 21 */     String param = conf.get("param");
/* 22 */     String[] arg = param.split(",");
/*    */     
/* 24 */     String newKey = "";
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 29 */     if (arg[0].split(":").length == 2) {
/* 30 */       BasicDBObject newObj = (BasicDBObject)val.get(arg[0].split(":")[0]);
/* 31 */       newKey = newObj.getString(arg[0].split(":")[1]);
/*    */     } else {
/* 33 */       newKey = (String)val.get(arg[0]); }
/* 34 */     context.write(new Text(newKey), this.one);
/*    */   }
/*    */ }


/* Location:              C:\Users\Federico\Downloads\Comuni-Italia-Project\Comuni-Italia-Project\comuni.jar!\bigdataman\belal\comuni\ComuniMapperNoCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */