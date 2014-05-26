##################
Change Request 002
##################

Änderungvorschläge auf Basis von Feedback durch Michael Borko und Erhard List
im Rahmen der Entwurfspräsentation am 2014-05-12.

==========
Änderungen
==========

~~~~~~~~~~~~~~~~~~~
REST statt JSON-RPC
~~~~~~~~~~~~~~~~~~~

*Representational State Transfer*, konzentriert sich im Gegensatz zu *SOAP*, das auch gerne für HTTP-Requests verwendet wird auf die URL am Server.
Die folgenden HTTP-Üblichen Methoden stehen zur Verfügung:
* GET
* POST
* ADD
* PUT
* DELETE 

Aufruf-Möglichkeit via Javascript:

Datentyp

.. code:: javascript

    function Contact(fName, lName, eMail, id) {
        this.fName = fName;
        this.lName = lName;
        this.eMail = eMail;
        this.ContactId = id;
        this.toJsonString = function () { return JSON.stringify(this); };
    };

Hinzufuegen:

.. code:: javascript

    function addContact (contact) {

         jQuery.ajax({
             type: "ADD",
             url: "http://localhost:49193/Contacts.svc/Add",
             data: contact.toJsonString(),
             contentType: "application/json; charset=utf-8",
             dataType: "json",
             success: function (data, status, jqXHR) {
                  // do something
             },
     
             error: function (jqXHR, status) {           
                  // error handler
             }

         });
    }

Siehe auch: https://github.com/jklepp-tgm/BigPicture/issues/2

----------
Abstimmung
----------

+-------------------+---------------+
| Wer?              | Dafür/Dagegen |
+===================+===============+
| Jakob Klepp       | Dafür         |
+-------------------+---------------+
| Martin Haidn      | Dafür         |
+-------------------+---------------+
| Djuric Daniel     | Dafür         |
+-------------------+---------------+

.. header::

    +-------------+-------------------+------------+
    | Titel       | Autor             | Date       |
    +=============+===================+============+
    | ###Title### | Jakob Klepp,      | 2014-05-13 |
    |             | Martin Haidn,     |            |
    |             | Daniel Djuric,    |            |
    |             | Mathias El-Far    |            |
    +-------------+-------------------+------------+

.. footer::

    ###Page### / ###Total###
