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
/*    */ public class ComuniMapperCondition
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
/* 24 */     String keyVal = "";
/* 25 */     String newKey = "";
/*    */     
/*    */ 
/* 28 */     if (arg[2].split(":").length == 2) {
/* 29 */       BasicDBObject newObj = (BasicDBObject)val.get(arg[2].split(":")[0]);
/* 30 */       newKey = newObj.getString(arg[2].split(":")[1]);
/*    */     } else {
/* 32 */       newKey = (String)val.get(arg[2]);
/*    */     }
/*    */     
/* 35 */     if (arg[0].split(":").length == 2) {
/* 36 */       BasicDBObject obj = (BasicDBObject)val.get(arg[0].split(":")[0]);
/* 37 */       keyVal = obj.getString(arg[0].split(":")[1]);
/*    */     } else {
/* 39 */       keyVal = (String)val.get(arg[0]);
/*    */     }
/*    */     
/* 42 */     if ((keyVal != null) && (keyVal.equals(arg[1]))) {
/* 43 */       context.write(new Text(newKey), this.one);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Federico\Downloads\Comuni-Italia-Project\Comuni-Italia-Project\comuni.jar!\bigdataman\belal\comuni\ComuniMapperCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */