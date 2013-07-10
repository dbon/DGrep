DGrep
=====

Cross-Platform GREP tool with GUI - also works with folders

![alt text](https://raw.github.com/dbon/dgrep/master/dgrep.png "DGrep v1.0")

1. Select the folder which contains the log files
2. Enter a search string
3. Select the file formats which should be included in your search.
4. Choose between the input file codec (default: UTF-8)
5. Decide if you want to open the search folder afterwards
6. Press the GREP button to start the search

Notice: After you've pressed GREP two files will be generated in the chosen path.
The first file (GREP-timestamp-all.txt) is a summary of all the files in which the search string was found.
The second file (GREP-timestamp-filtered.txt) is the important one.
It includes only the rows that contain the search string.

