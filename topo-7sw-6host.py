"""Custom topology example

Two directly connected switches plus a host for each switch:

   host --- switch --- switch --- host

Adding the 'topos' dict with a key/value pair to generate our newly defined
topology enables one to pass in '--topo=mytopo' from the command line.
"""

from mininet.topo import Topo

class MyTopo( Topo ):
    "Simple topology example."

    def __init__( self ):
        "Create custom topo."

        # Initialize topology
        Topo.__init__( self )

        # Add Hosts
        h1 = self.addHost( 'h1' )
        h2 = self.addHost( 'h2', mac='8a:4d:1e:fd:ec:8e')
        h3 = self.addHost( 'h3' )
        h4 = self.addHost( 'h4' )
        h5 = self.addHost( 'h5' )
        h6 = self.addHost( 'h6', mac='9e:fb:94:d7:d8:6d')
        #Add Switches
        S1 = self.addSwitch( 's1'  )
        S2 = self.addSwitch( 's2' )
        S3 = self.addSwitch( 's3' )
        S4 = self.addSwitch( 's4' )
        S5 = self.addSwitch( 's5'  )
        S6 = self.addSwitch( 's6'  )
        S7 = self.addSwitch( 's7' )
        # Add links
        self.addLink( S1,S2)
        self.addLink( S1,S3)
        self.addLink( S2,S4)
        self.addLink( S2,S5)
        self.addLink( S3,S6)
        self.addLink( S3,S7)
        self.addLink( S4,h1)
        self.addLink( S4,h2)
        self.addLink( S5,h3)
        self.addLink( S6,h4)
        self.addLink( S7,h5)
        self.addLink( S7,h6)


topos = { 'mytopo': ( lambda: MyTopo() ) }
