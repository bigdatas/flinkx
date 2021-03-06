/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.flinkx.hdfs.writer;

import com.dtstack.flinkx.outputformat.RichOutputFormatBuilder;
import org.apache.commons.lang.StringUtils;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;

/**
 * The builder class of HdfsOutputFormat
 *
 * Company: www.dtstack.com
 * @author huyifan.zju@163.com
 */
public class HdfsOutputFormatBuilder extends RichOutputFormatBuilder {

    private HdfsOutputFormat format;

    public HdfsOutputFormatBuilder(String type) {
        switch(type.toUpperCase()) {
            case "TEXT":
                format = new HdfsTextOutputFormat();
                break;
            case "ORC":
                format = new HdfsOrcOutputFormat();
                break;
            case "PARQUET":
                format = new HdfsParquetOutputFormat();
                break;
            default:
                throw new IllegalArgumentException("Unsupported HDFS file type: " + type);
        }

        super.format = format;
    }

    public void setColumnNames(List<String> columnNames) {
        format.columnNames = columnNames;
    }

    public void setColumnTypes(List<String> columnTypes) {
        format.columnTypes = columnTypes;
    }

    public void setHadoopConfig(Map<String,String> hadoopConfig) {
        format.hadoopConfig = hadoopConfig;
    }

    public void setFullColumnNames(List<String> fullColumnNames) {
        format.fullColumnNames = fullColumnNames;
    }

    public void setDelimiter(String delimiter) {
        format.delimiter = delimiter;
    }

    public void setRowGroupSize(int rowGroupSize){
        format.rowGroupSize = rowGroupSize;
    }

    public void setFullColumnTypes(List<String> fullColumnTypes) {
        format.fullColumnTypes = fullColumnTypes;
    }

    public void setDefaultFS(String defaultFS) {
        format.defaultFS = defaultFS;
    }

    public void setWriteMode(String writeMode) {
        this.format.writeMode = StringUtils.isBlank(writeMode) ? "APPEND" : writeMode.toUpperCase();
    }

    public void setPath(String path) {
        this.format.path = path;
    }

    public void setFileName(String fileName) {
        format.fileName = fileName;
    }

    public void setCompress(String compress) {
        format.compress = compress;
    }

    public void setCharSetName(String charsetName) {
        if(StringUtil.isNotEmpty(charsetName)) {
            if(!Charset.isSupported(charsetName)) {
                throw new UnsupportedCharsetException("The charset " + charsetName + " is not supported.");
            }
            this.format.charsetName = charsetName;
        }

    }

    @Override
    protected void checkFormat() {
        if (format.path == null || format.path.length() == 0) {
            throw new IllegalArgumentException("No path supplied.");
        }

        if (format.defaultFS == null || format.defaultFS.length() == 0) {
            throw new IllegalArgumentException("No defaultFS supplied.");
        }

        if (!format.defaultFS.startsWith("hdfs://")) {
            throw new IllegalArgumentException("defaultFS should start with hdfs://");
        }
    }

}
