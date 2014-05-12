##################
Change Request 001
##################

Änderungvorschläge auf Basis von Feedback durch Michael Borke und Erhard List
im Rahmen der Entworfspräsentation am 2014-05-12.

==========
Änderungen
==========

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Verwendung des Command Pattern
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Im Command Pattern werden Methodenaufrufe, inklusive Parameter in Objekte
gekapselt. Damit lassen sich Commands in Warteschlangen anreihen, loggen sowie
einfacher wieder rückgängig machen.

Neben den genannten Vorteilen kann ein mit dem Command Pattern aufgebautes 
System um weitere Commands erweitert werden ohne bestehenden Code zu ändern.

Dafür wird aus einem einzigen Methoden aufruf ein ganzer Haufen dieser und ein
Command eine Klasse statt einer Methode.

.. image:: https://upload.wikimedia.org/wikipedia/commons/9/93/KommandoMuster_Klassen.png
    :width: 40%

----------
Abstimmung
----------

+-------------------+---------------+
| Wer?              | Dafür/Dagegen |
+===================+===============+
|                   |               |
+-------------------+---------------+

.. header::

    +-------------+-------------------+------------+
    | Titel       | Autor             | Date       |
    +=============+===================+============+
    | ###Title### | Jakob Klepp,      | 2014-05-12 |
    |             | Martin Haidn,     |            |
    |             | Daniel Djuric,    |            |
    |             | Mathias El-Far    |            |
    +-------------+-------------------+------------+

.. footer::

    ###Page### / ###Total###
