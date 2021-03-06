<!--
    Licensed to the Apache Software Foundation (ASF) under one or more 
    contributor license agreements.  See the NOTICE file distributed with
    this work for additional information regarding copyright ownership. 
    The ASF licenses this file to you under the Apache License, Version 2.0
    (the "License"); you may not use this file except in compliance with 
    the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and 
    limitations under the License.
-->

# What is spatial index

[A spatial index](https://gistbok.ucgis.org/topic-keywords/indexing) is a data structure that allows for accessing a spatial object efficiently. It is a common technique used by spatial databases.  Without indexing, any search for a feature would require a "sequential scan" of every record in the database, resulting in much longer processing time. In a spatial index construction process, the minimum bounding rectangle serves as an object approximation. Various types of spatial indices across commercial and open-source databases yield measurable performance differences. Spatial indexing techniques are playing a central role in time-critical applications and the manipulation of spatial big data.



# How does CarbonData implement spatial index

There are many open source implementations for spatial indexing and to process spatial queries. CarbonData implements a different way of spatial index. Its core idea is to use the raster data. Raster is made up of matrix of cells organized into rows and columns(called a grid). Each cell represents a coordinate. The index for the coordinate is generated using longitude and latitude, like the [Z order curve](https://en.wikipedia.org/wiki/Z-order_curve).

CarbonData rasterize the user data during data load into segments. A set of latitude and longitude represents a grid range. The size of the grid can be configured. Hence, the coordinates loaded are often discrete and not continuous.

Below figure shows the relationship between the grid and the points residing in it. Black point represents the center point of the grid, and the red points are the coordinates at the arbitrary positions inside the grid. The red points can be replaced by the center point of the grid to indicate that the points lies within the grid. During data load, CarbonData generates an index for the coordinate according to row and column of the grid(in the raster) where that coordinate lies. These indices are the same as Z order. For the detailed conversion algorithm, please refer to the design documents of spatial index.

![File Directory Structure](../docs/images/spatial-index-1.png?raw=true)

Carbon supports Polygon User Defined Function(UDF) as filter condition in the query to return all the data points lying within it. Polygon UDF takes multiple points(i.e., pair of longitude and latitude) separated by a comma. Longitude and latitude in the pair are separated by a space. The first and last points in the polygon must be same to form a closed loop. CarbonData builds a quad tree using this polygon and spatial region information passed while creating a table. The nodes in the quad tree are composed of indices generated by the row and column information projected in the polygon area. When the grid center point lies within the polygon area, the grid is considered as selected. In the following figure, user selects a quadrilateral shaped polygon. The grid at the center of the region is chosen to build a quad tree. Once tree is build, all the leafs are scanned to get the list of range of indices(with each range consisting of minimum index and maximum index in the range). All the indices starting from minimum to maximum in each range forms the result.
The main reasons for faster query response are as follows :
* Data is sorted based on the index values.
* Polygon UDF filter is pushed down from engine to the carbon layer such that CarbonData scans only matched blocklets avoiding full scan.

![File Directory Structure](../docs/images/spatial-index-2.png?raw=true)


# Installation and Deployment

Geo is a separate module in the Project. It can be included or excluded from the project build based on the requirement.

## Basic Command

### Create Table

Create table with spatial index table properties

```
create table source_index(id BIGINT, latitude long, longitude long) stored by 'carbondata' TBLPROPERTIES (
'SPATIAL_INDEX'='mygeohash',
'SPATIAL_INDEX.mygeohash.type'='geohash',
'SPATIAL_INDEX.mygeohash.sourcecolumns'='longitude, latitude',
'SPATIAL_INDEX.mygeohash.originLatitude'='19.832277',
'SPATIAL_INDEX.mygeohash.gridSize'='50',
'SPATIAL_INDEX.mygeohash.minLongitude'='1.811865',
'SPATIAL_INDEX.mygeohash.maxLongitude'='2.782233',
'SPATIAL_INDEX.mygeohash.minLatitude'='19.832277',
'SPATIAL_INDEX.mygeohash.maxLatitude'='20.225281',
'SPATIAL_INDEX.mygeohash.conversionRatio'='1000000');
```
Note: `mygeohash` in the above example represent the index name.

#### List of spatial index table properties

|Name|Description|
|-----------------------------------|-----------------------------------------------------------------------------------------|
| SPATIAL_INDEX | Used to configure Spatial Index name. This name is appended to `SPATIAL_INDEX` in the subsequent sub-property configurations. `xxx` in the below sub-properties refer to index name.|
| SPATIAL_INDEX.xxx.type | Type of algorithm for processing spatial data. Currently, supports only 'geohash'.|
| SPATIAL_INDEX.xxx.sourcecolumns | longitude and latitude column names as in the table. These columns are used to generate index value for each row.|
| SPATIAL_INDEX.xxx.gridSize | Grid size of raster data in metres. Currently, spatial index supports raster data.|
| SPATIAL_INDEX.xxx.minLongitude | Minimum longitude of the gridded rectangular area.|
| SPATIAL_INDEX.xxx.maxLongitude | Maximum longitude of the gridded rectangular area.|
| SPATIAL_INDEX.xxx.minLatitude | Minimum latitude of the gridded rectangular area.|
| SPATIAL_INDEX.xxx.maxLatitude | Maximum latitude of the gridded rectangular area.|
| SPATIAL_INDEX.xxx.conversionRatio | Conversion factor. It allows user to translate longitude and latitude to long. For example, if the data to load is longitude = 13.123456, latitude = 101.12356. User can configure conversion ratio sub-property value as 1000000, and change data to load as longitude = 13123456 and latitude = 10112356. Operations on long is much faster compared to floating-point numbers.|
| SPATIAL_INDEX.xxx.class | Optional user custom implementation class. Value is fully qualified class name.|


### Select Query

Query with Polygon UDF predicate

```
select * from source_index where IN_POLYGON('16.321011 4.123503,16.137676 5.947911,16.560993 5.935276,16.321011 4.123503')
```

## Reference

```
[1] https://issues.apache.org/jira/browse/CARBONDATA-3548
[2] https://gistbok.ucgis.org/topic-keywords/indexing
[3] https://en.wikipedia.org/wiki/Z-order_curve
```