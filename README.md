YMLbook
=======

Bukkit plugin - YMLbook is simple book customizing / writing plugin.



<<center>>{{http://i.imgur.com/rdlatSa.png|YMLbook}}<</center>>
<<center>>{{http://exel80.me/kuvat/gp/reunaV.png|}}[[http://dev.bukkit.org/server-mods/ymlbook/|{{http://exel80.me/kuvat/gp/FEATURESs.png|Feature page}}]]{{http://exel80.me/kuvat/gp/VIIVA.png|}}[[http://dev.bukkit.org/bukkit-plugins/ymlbook/pages/changelog/|{{http://exel80.me/kuvat/gp/CHANGELOG.png|ChangeLog page}}]]{{http://exel80.me/kuvat/gp/reunaO.png|}}<</center>>

<<center>>**YMLbook is simple book customizing / writing plugin.**<</center>>
<<center>>**You can easily write books as much you want. Example your server rules, admin and moderator list, donation links, tutorial how to use game/commands and everything what you want. Just write book information to books.yml and players can copy it and read it.**\\
\\
[[https://github.com/exel80/YMLbook|{{http://i.imgur.com/OQwUyFT.png|Open source}}]]<</center>>

<<center>>{{http://i.imgur.com/l8XTtsh.png|}}<</center>>

== Features==
* You can make as many books you want.
* Really easy to use and setup.
* Can make sign which give the book. [[http://dev.bukkit.org/bukkit-plugins/ymlbook/images/5-ymlbook-sign/|(picture)]]
* Admin command, give the book to player.
* http://minecraftwiki.net/wiki/Formatting_codes works. **(Dont use alt codes)**
* Give permission each book //(ymlbook.book.BOOKNAME)//.
* Automatically make new page //(one page can be 255 length)//.
* You can full customization messages.
* Can add lore text to the book.
* Permissions & commands.

<<center>>{{http://i.imgur.com/l8XTtsh.png|}}<</center>>

== Config==
Write book infromation to books.yml (book title, author and pages) and everybody who have permission/op can copy it to inventory.\\

**books.yml**
<<code yml>>
book:
 rules:
  title: Rules
  author: SERVER NAME
  lore: HOLY S***, DO YOU SEE THIS, IN MINECRAF?!
  text: |
    1. Txt here..
    2. Txt here..
    3. Txt here..
    4. Txt here..
    5. Txt here..
 readme:
  title: Welcome to use YMLbook
  author: exel80
  lore: Version 1.4
  text: |
    1.4 versio is here! Finally i get this released :)
    We have lots of new features example: Lore, bookgive and much more cool stuff.
    
    Hope you enjoy,
    Exel80
<</code>>

Here is example how to make own book,\\
copy+paste this to books.yml and try command, /book test
<<code yml>>
 test:
  title: TEST
  author: TEST
  lore: false
  text: |
    TEST
<</code>>

If you are OP then you can use all books automatically, but if you want give member access to get the book example rules. You give //ymlbook.book.rules// permission to member group.\\
\\
**If you need any help to setup this plugin, you can send me pm**\\
\\
Here is also helpful picture about if you want text to new line,\\
http://puu.sh/3V6cc/abef1d3158.png

<<center>>{{http://i.imgur.com/l8XTtsh.png|}}<</center>>

== Command / Permission ==
|=Command|=Description|=Permission|
|/book <bookname>|Get the book|ymlbook.use|
|/bookgive <player> <bookname>|Give book to player|ymlbook.bookgive|
|/bookreload|Reload config.yml|ymlbook.reload|

== Other permissions ==
|=Permission|=Description|
|ymlbook.book.*|Give access to use/copy all books|
|ymlbook.book.NAME //(ex. ymlbook.book.readme)//|Give access to use/copy one book.|
|ymlbook.signbook|Can make sign which gives the book.|

<<center>>{{http://i.imgur.com/l8XTtsh.png|}}<</center>>

== MCStats / Metrics==
[[http://mcstats.org/plugin/YMLbook|{{http://mcstats.org/signature/YMLbook.png}}]]

<<center>>{{http://i.imgur.com/l8XTtsh.png|}}<</center>>

== Todo list ==
* Make "alt code" to work.
* /bookcopy <bookname> //Command to add the book in Minecraft to book.yml file.//
* /booklist //See all of the books, which are permission to use.//
* First join book //(Get when player join first time)//
* Start new page right away //(Add "function" to start new page)//
* --Sign book give. [Book] name--
* --Move from config.yml book: book text to own yml file. //(to book.yml)//--
* --/bookgive <player> <bookname> //Command to admin. Admin can give book to player.//--
* --Color support--
* --Lore text--
* --Give permission each book //(Example: ymlbook.book.rules)//--
* --Custom messages--
* --Reload config command--
* --Add metrics--
* Any suggestions?

== Known bugs ==
* Don't use alt codes in books.yml\\
//(If you find any bugs please report it to the manager)//

<<center>>{{http://i.imgur.com/l8XTtsh.png|}}<</center>>

== Pictures ==
[[http://dev.bukkit.org/server-mods/ymlbook/images/4-ymlbook-in-action/|{{http://dev.bukkit.org/media/images/50/812/ymlbookshowing.png|YMLbook in action}}]]\\

**YMLBook 1.5 new feature**
[[http://dev.bukkit.org/bukkit-plugins/ymlbook/images/5-ymlbook-sign/|{{http://dev.bukkit.org/media/images/58/654/2013-07-01_22.58.00.png|SignBook}}]]
