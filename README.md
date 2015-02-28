# PACGameDev

This is a little 2D game engine. The goal for this project is to encourage
novice/intermediate programers to practice programming in Java. 
So this library makes it as simple as possible to build easy 
games like Pong (which is one of the included examples).

It currently handels collision detection and simple bounce rules.
It's a work in progress.

The documentation is hosted on a dinky little web server
<a href="http://ejk314.ddns.net/">here</a>; don't be surprised by
downtime. You can also generate the docs from source with `make docs`.

You can then run any of the 4 examples:

    ./runExample.sh BounceEngineExample
    ./runExample.sh BallChase
    ./runExample.sh Pong
    ./runExample.sh Jump

I appologize that the project is only set up for Linux at the
moment. Making it Windows (& Mac ?) friendly is on the TODO list:

TODO
----

* Document the existing code.
* Create Install/Getting-Started Tutorial
    * Linux
    * Windows
    * Mac?
* Release Binary
