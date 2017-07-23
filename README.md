# csv-manager
## This project :
* reads a mapping file from src/main/resources/com/home/csv//mapping and keep that in memory as map with key as staffId
and value as the complete row of csv. For example: (key:12345671, value:Mapping{staffId=12345671, gbGf='GBGF1', svcLine='SvcLine1'}
* then it will read all csv files from src/main/resources/com/home/csv/input directory one-by-one and creates a list.
For example: (key:12345671, value:Mapping{staffId=12345671, gbGf='GBGF1', svcLine='SvcLine1'}
* then it creates a master data file based on the staffId read from each object in list and the querying that staffId
from mapping map and then setting the values obtained from the map into one consolidated row. For example: [12345678, Folder1, 11, GBGF8, SvcLine8]
