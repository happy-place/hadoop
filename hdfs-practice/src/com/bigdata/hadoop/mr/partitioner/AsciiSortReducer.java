package com.bigdata.hadoop.mr.partitioner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class AsciiSortReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable v = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        Iterator<IntWritable> iter = values.iterator();
        while (iter.hasNext()) {
            count += iter.next().get();
        }
        v.set(count);
        context.write(key, v);
    }
}
