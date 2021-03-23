/*    */ package bigdataman.belal.comuni;
/*    */ 
/*    */ import com.mongodb.hadoop.MongoConfig;
/*    */ import com.mongodb.hadoop.MongoInputFormat;
/*    */ import com.mongodb.hadoop.MongoOutputFormat;
/*    */ import com.mongodb.hadoop.io.BSONWritable;
/*    */ import com.mongodb.hadoop.util.MongoConfigUtil;
/*    */ import com.mongodb.hadoop.util.MongoTool;
/*    */ import org.apache.hadoop.conf.Configuration;
/*    */ import org.apache.hadoop.io.IntWritable;
/*    */ import org.apache.hadoop.io.Text;
/*    */ import org.apache.hadoop.mapred.JobConf;
/*    */ 
/*    */ public class MapReduceTool
/*    */   extends MongoTool
/*    */ {
/*    */   public MapReduceTool(String s, String collectionName)
/*    */   {
/* 19 */     JobConf conf = new JobConf(new Configuration());
/* 20 */     conf.set("param", s);
/* 21 */     String[] arg = s.split(",");
/*    */     
/* 23 */     MongoConfigUtil.setInputFormat(conf, MongoInputFormat.class);
/* 24 */     MongoConfigUtil.setOutputFormat(conf, MongoOutputFormat.class);
/* 25 */     MongoConfig config = new MongoConfig(conf);
/*    */     
/* 27 */     if (arg.length == 3) {
/* 28 */       config.setMapper(ComuniMapperCondition.class);
/* 29 */     } else if (arg.length == 1) {
/* 30 */       config.setMapper(ComuniMapperNoCondition.class);
/*    */     }
/* 32 */     config.setReducer(ComuniReducer.class);
/* 33 */     config.setMapperOutputKey(Text.class);
/* 34 */     config.setMapperOutputValue(IntWritable.class);
/* 35 */     config.setOutputKey(BSONWritable.class);
/* 36 */     config.setOutputValue(IntWritable.class);
/* 37 */     config.setInputURI("mongodb://localhost:27017/comuni.comuni");
/* 38 */     config.setOutputURI("mongodb://localhost:27017/comuni.aggregation_" + collectionName);
/* 39 */     setConf(conf);
/*    */   }
/*    */ }


/* Location:              C:\Users\Federico\Downloads\Comuni-Italia-Project\Comuni-Italia-Project\comuni.jar!\bigdataman\belal\comuni\MapReduceTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */