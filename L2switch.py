from pox.core import core
from pox.openflow import libopenflow_01 as of 
from pox.lib.addresses import EthAddr
import pox.openflow.libopenflow_01 as of


rules = [['8a:4d:1e:fd:ec:8e','9e:fb:94:d7:d8:6d']]

class MyController(object):
	"""docstring for MyController"""
	def __init__(self):
		core.openflow.addListeners(self)
	def _handle_ConnectionUp(self,event):
		L2Switch(event.connection)
		for rule in rules:
			block = of.ofp_match()
			block.dl_src = EthAddr(rule[0])
			block.dl_dst = EthAddr(rule[1])
			flow_mod = of.ofp_flow_mod()
			flow_mod.match = block
			event.connection.send(flow_mod)





class L2Switch(object):
	def __init__(self, connection):
		print "L2 Switch"
		self.mactable = {}
		self.connection = connection
		connection.addListeners(self)
	def _handle_PacketIn(self,pkt_in_event):
		packet = pkt_in_event.parse()
		print "pkt: src", packet.src, "dst ", packet.dst ," port ", pkt_in_event.port
		self.mactable[packet.src] = pkt_in_event.port
		if packet.dst not in self.mactable:
			print "Destination Unknown, Flooding"
			self.flood(pkt_in_event)
		else:
			self.foward(pkt_in_event,self.mactable[packet.dst])

	def flood(self,pkt_in_event):
		msg = of.ofp_packet_out()
		msg.actions.append(of.ofp_action_output(port = of.OFPP_FLOOD))
		msg.buffer_id =pkt_in_event.ofp.buffer_id
		msg.data = pkt_in_event.ofp
		msg.in_port = pkt_in_event.port
		self.connection.send(msg)
	def drop(self, pkt_in_event):
		msg = of.ofp_packet_out()
		msg.buffer_id = pkt_in_event.ofp.buffer_id
		msg.in_port = pkt_in_event.port
		self.connection.send(msg)
	def foward(self,pkt_in_event,to_port):
		packet = pkt_in_event.parse()
		msg = of.ofp_flow_mod()
		msg.match = of.ofp_match.from_packet(packet)
		msg.actions.append(of.ofp_action_output(port = to_port))
		msg.buffer_id = pkt_in_event.ofp.buffer_id
		msg.data = pkt_in_event.ofp
		self.connection.send(msg)
def launch():
	core.registerNew(MyController)
		