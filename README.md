# NET4005A
=================================================
- CODE written by Samuel Iwuno

## CODE Breakdown
====================================================
- L2switch.py - this contains the controller pox code. it acts as the control plane, pushing flow rules to switch
- topo-7sw-6host.py - this contains code that creates the topology needed for the assignment.



## Run Process
===================================================================================
- put  L2switch.py in pox/pox/misc
- put  topo-7sw-6host.py in mininet/custom

## open 2 terminals
-----------------------------
- in first terminal
	cd pox
	./pox.py misc.L2switch.py
- in second terminal
	cd mininet/custom
	sudo mn --custom topo-7sw-6host.py --topo mytopo --controller=remote,ip=127.0.0.1
perform ping tests on mininet


## NOTES
======================================================================================
code for H2/H6 Blocking rules are from line 7 to 21 in L2switch.py
	
