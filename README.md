# SC2002Project

Welcome to Camp Application and Management System (CAMS)

LAB group: `SMACS` \
Group Number: `6` \
Members: `Ferguson`, `Chin Yi`, `Enric`, `Jun Kiat`, `Ying Hao`

Click [here](https://drive.google.com/drive/folders/1TfUKbkJdoM-3EOIhEjAw5re0QFtx4_bK?usp=sharing) for the Javadocs.

# Installation

I have pre-compiled the .class files already, so you can just run the CAMSapp to start

```
$ java CAMSapp
```

However, if there exists compilation errors, please run the following

### Step 1: Delete all the .class files

Ensure that your system have python before running.

```
$ python deleteclass.py
```

### Step 2: Recompile

Create all the `*.class` files.

```
$ javac .\CAMSapp.java
```

You may start the program now.

# Start the program

```

 ██████╗ █████╗ ███╗   ███╗███████╗
██╔════╝██╔══██╗████╗ ████║██╔════╝
██║     ███████║██╔████╔██║███████╗
██║     ██╔══██║██║╚██╔╝██║╚════██║
╚██████╗██║  ██║██║ ╚═╝ ██║███████║
 ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝
╔═════════════════════════════════════════════════════════════════╗
║ Camp Application & Management System                            ║
╚═════════════════════════════════════════════════════════════════╝
1. Login
2. Exit
Enter your choice:
```

Login to the respective account(s) and you may begin using the APP!
All the passwords are set to default `password`.

#### Staff accounts

```
userID: HUKUMAR
userID: OURIN
userID: UPAM
userID: ANWIT
userID: ARVI

password: password
```

#### Student accounts (Non-camp committee member)

```
userID: BR015
userID: YCHERN
userID: CT113

password: password
```

#### Student accounts (Camp committee member)

```
userID: KOH1

password: pw
```

I only listed some of the accounts here.
Please check `csvfiles\usersStaff.csv` or `csvfiles\usersStudent.csv` for other accounts to login to.
