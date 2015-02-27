# PACGameDev

This is a simple little 2D game engine. The goal here is for 
novice/intermediate programers to practice programming in Java. 
So this library makes it very simple to build small, simple
games like Pong (which is one of the included examples).

It currently handels collision detection and simple bounce rules.
Right now, I'm working on documenting all the code.

To view the documentation, you'll need to build the project
(it's all javadoc-generated). Just run `make docs` from the project 
directory to build the jars and generate the documentation.

You can then run any of the 4 examples:

    ./runExample.sh BounceEngineExample
    ./runExample.sh BallChase
    ./runExample.sh Pong
    ./runExample.sh Jump

I appologize that the project is only set up for linux at the
moment. Making it Windows (& Mac ?) friendly is on the TODO list:

TODO
----

* Document the existing code.
* Create Install/Getting-Started Tutorial
    * Linux
    * Windows
    * Mac?
* Release Binary
