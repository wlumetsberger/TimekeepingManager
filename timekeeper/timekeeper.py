#!/usr/bin/python
 
import tornado.web
import tornado.websocket
import tornado.ioloop
import MFRC522
import signal
import sys
import RPi.GPIO as GPIO
import time
import threading
import requests
import json
import logging

logging.basicConfig(filename='timekeeper.log', level=logging.INFO, format='%(asctime)s %(message)s');

class WorkerThread(threading.Thread):
	def __init__(self, threadID, name, counter, race_id):
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.counter = counter
		self.is_running = False;
		self.race_id = race_id;
		
	def exit_thread(self):
		self.is_running = False;
	def run(self):
		self.is_running = True;
		logging.info('start race whith id: '+ str(self.race_id));
		tagUId_old = "";
		MIFAREReader = MFRC522.MFRC522();
		while(self.is_running):
				(status,TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
				if status == MIFAREReader.MI_OK:
					(status,backData) = MIFAREReader.MFRC522_Anticoll()
					if(len(backData) < 5):
						logging.debug("cannot read card id correct");
					else:
						tagUId = str(backData[0])+"@"+str(backData[1])+"@"+str(backData[2])+"@"+str(backData[3])+"@"+str(backData[4])
					logging.debug('tagUid:' + str(tagUId));
					if tagUId != tagUId_old:
						logging.info("tagUid:" + str(tagUId));
						data = json.dumps({'command':'scan','raceId':str(self.race_id),'tagId':tagUId});
						tornado.ioloop.IOLoop.instance().add_callback(WebSocketHandler.push_client,data)
						try:
							data = json.dumps({'raceId':str(self.race_id),'tagId':tagUId})
							logging.info('insert lap: '+str(data));
							response = requests.post("http://10.0.0.80:8080/lap/insert?raceId="+str(self.race_id)+"&tagId="+tagUId);
							tagUId_old = tagUId;
						except:
							logging.info("Exception occured during insert ");
		logging.info("do cleanup")
		MFRC522.MFRC522_cleanUp
class WebSocketHandler(tornado.websocket.WebSocketHandler):
	
	connections = set()
	global thread
	
	def check_origin(self, origin):
		return True
	def open(self):
		logging.info("New client connected");
		self.connections.add(self)

	def on_message(self, message):
		global thread
		jsonMessage = json.loads(message);
		if(jsonMessage['command']=="start"):
			logging.info("start game");
			if(thread.isAlive()):
				logging.info("Thread allready started");
			else:
				raceId = jsonMessage['raceId']
				thread = WorkerThread(1,"Thread1",1,raceId);
				thread.start(); 
			
			
		if(jsonMessage['command']=="stop"):
			logging.info("stop game");
			thread.exit_thread();
			logging.info("thread beended:" + str(thread.isAlive()));
		if(jsonMessage['command']=="readSingle"):
			logging.info("read singleId");
			self.readSingle();
	def readSingle(self):
		tagUId_old = "";
		MIFAREReader = MFRC522.MFRC522();
		tagUId = "";
		tagUId_old = "";
		while tagUId_old == tagUId: 
			(status,TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
			if status == MIFAREReader.MI_OK:
				(status,backData) = MIFAREReader.MFRC522_Anticoll()
				if status == MIFAREReader.MI_OK:
					tagUId = str(backData[0])+"@"+str(backData[1])+"@"+str(backData[2])+"@"+str(backData[3])+"@"+str(backData[4])
					logging.info (tagUId)
					data = json.dumps({'command':'singleScan','tagId':tagUId});	
					self.push_client(data);
		MFRC522.MFRC522_cleanUp
	def on_close(self):
		self.connections.remove(self)
		logging.info("Client disconnected");
	
	@classmethod
	def push_client(cls, msg):
		logging.debug('writing to clients' + msg);
		for client in cls.connections:
			client.write_message(msg)

thread = WorkerThread(1,"Thread1",1,0)
application = tornado.web.Application([
    (r"/", WebSocketHandler),
])
 
if __name__ == "__main__":
    application.listen(4711)
    tornado.ioloop.IOLoop.instance().start()