/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.huawei.unibi.molap.sortandgroupby.sortData;

import java.io.File;

public class FileMergerParameters {
    /**
     * intermediateFiles
     */
    private File[] intermediateFiles;

    /**
     * fileBufferSize
     */
    private int fileReadBufferSize;

    /**
     * fileWriteSize
     */
    private int fileWriteBufferSize;

    /**
     * measure count
     */
    private int measureColCount;

    /**
     * measure count
     */
    private int dimColCount;

    /**
     * complexDimColCount
     */
    private int complexDimColCount;

    /**
     * measure count
     */
    private int highCardinalityCount;

    /**
     * outFile
     */
    private File outFile;

    /**
     * sortTempFileNoOFRecordsInCompression
     */
    private int noOfRecordsInCompression;

    /**
     * isSortTempFileCompressionEnabled
     */
    private boolean isCompressionEnabled;

    /**
     * prefetch
     */
    private boolean prefetch;

    /**
     * prefetchBufferSize
     */
    private int prefetchBufferSize;

    public File[] getIntermediateFiles() {
        return intermediateFiles;
    }

    public void setIntermediateFiles(final File[] intermediateFiles) {
        this.intermediateFiles = intermediateFiles;
    }

    public int getFileReadBufferSize() {
        return fileReadBufferSize;
    }

    public void setFileReadBufferSize(int fileReadBufferSize) {
        this.fileReadBufferSize = fileReadBufferSize;
    }

    public int getFileWriteBufferSize() {
        return fileWriteBufferSize;
    }

    public void setFileWriteBufferSize(int fileWriteBufferSize) {
        this.fileWriteBufferSize = fileWriteBufferSize;
    }

    public int getMeasureColCount() {
        return measureColCount;
    }

    public void setMeasureColCount(int measureColCount) {
        this.measureColCount = measureColCount;
    }

    public int getDimColCount() {
        return dimColCount;
    }

    public void setDimColCount(int dimColCount) {
        this.dimColCount = dimColCount;
    }

    public int getComplexDimColCount() {
        return complexDimColCount;
    }

    public void setComplexDimColCount(int complexDimColCount) {
        this.complexDimColCount = complexDimColCount;
    }

    public File getOutFile() {
        return outFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    public int getNoOfRecordsInCompression() {
        return noOfRecordsInCompression;
    }

    public void setNoOfRecordsInCompression(int noOfRecordsInCompression) {
        this.noOfRecordsInCompression = noOfRecordsInCompression;
    }

    public boolean isCompressionEnabled() {
        return isCompressionEnabled;
    }

    public void setCompressionEnabled(boolean isCompressionEnabled) {
        this.isCompressionEnabled = isCompressionEnabled;
    }

    public boolean isPrefetch() {
        return prefetch;
    }

    public void setPrefetch(boolean prefetch) {
        this.prefetch = prefetch;
    }

    public int getPrefetchBufferSize() {
        return prefetchBufferSize;
    }

    public void setPrefetchBufferSize(int prefetchBufferSize) {
        this.prefetchBufferSize = prefetchBufferSize;
    }

    /**
     * @return the highCardinalityCount
     */
    public int getHighCardinalityCount() {
        return highCardinalityCount;
    }

    /**
     * @param highCardinalityCount the highCardinalityCount to set
     */
    public void setHighCardinalityCount(int highCardinalityCount) {
        this.highCardinalityCount = highCardinalityCount;
    }
}
