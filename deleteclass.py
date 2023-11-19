import glob
import os


# Run this Python program to delete all the .class files first before compilation
# Alternatively, u can use cmd find to delete all the .class files
# After running this program, you can run javac .\CAMSapp.java


def deleteFiles(files):
    for f in files:
        if os.path.isdir(f):
            deleteFiles(glob.glob(f"{f}/*"))
        else:
            if f.find(".class") != -1:
                os.remove(f)


if __name__ == "__main__":
    start = glob.glob("./*")
    deleteFiles(start)
