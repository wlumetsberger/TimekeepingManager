'''
Created on 22.05.2014

@author: Wolfgang
'''
'''
Created on 19.05.2014

@author: Wolfgang
'''

import MFRC522

# Here the main program starts with reading and so on..


tagUId_old = ''
tagUId = ''
MIFAREReader = MFRC522.MFRC522()
 
while tagUId_old == tagUId: 
    (status,TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
    if status == MIFAREReader.MI_OK:
        (status,backData) = MIFAREReader.MFRC522_Anticoll()
        if status == MIFAREReader.MI_OK:
            tagUId = str(backData[0])+"@"+str(backData[1])+"@"+str(backData[2])+"@"+str(backData[3])+"@"+str(backData[4])
            print (tagUId)
MFRC522.MFRC522_cleanUp
           
           
