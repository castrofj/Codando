import os
def rename_files():
    #1 get file names from a folder
    file_list = os.listdir(r"C:\Users\ferca\OneDrive\EAD\Udacity\Python\prank_exercise\pictures")
    saved_path = os.getcwd()
    os.chdir(r"C:\Users\ferca\OneDrive\EAD\Udacity\Python\prank_exercise\pictures")
    print("Current working directory: "+saved_path)
    #2 for each file, rename file
    for file_name in file_list:
        print("Old name - "+file_name)
        print("New name - "+file_name.translate(None, "0123456789"))
        os.rename(file_name, file_name.translate(None, "0123456789"))
    os.chdir(saved_path)
rename_files()