var $$ = Dom7;

var app = new Framework7({
  root: '#app',
  id: 'io.johndavemanuel.heyu',
  name: 'HeyU',
  theme: 'md',
  routes: routes,
});

var mainView = app.views.create('.view-main', {
  url: '/'
});

var messages;
$$(document).on('page:init', '.page[data-name="messages"]', function (e) {
  // Init Messages
  history(to);
   messages = app.messages.create({
    el: '.messages',

    // First message rule
    firstMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;

      if (!previousMessage || previousMessage.type !== message.type || previousMessage.name !== message.name) return true;
      return false;
    },
    // Last message rule
    lastMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;

      if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
      return false;
    },
    // Last message rule
    tailMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;

      if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
      return false;
    }
  });





  $.each(grouphistory, function (index, obj) {
    var from = obj.from;
    var objto = obj.to;
    var time = obj.time;
    var content = obj.content
    var type = obj.type;
    console.log("to.id "+to.id);
    console.log("objto.id "+objto.id);
    if(to.id==objto.id) {
      if (from.name == user.name) {
        messages.addMessage({
          text: content,
          name: from.name,
          type: 'sent',
          avatar: from.avatarUrl
        });
      } else {
        messages.addMessage({
          text: content,
          type: 'received',
          name: from.name,
          avatar: from.avatarUrl
        });
      }
    }
  });


  // Init Messagebar
  var messagebar = app.messagebar.create({
    el: '.messagebar'
  });

  // Response flag
  var responseInProgress = false;

  // Send Message
  $$('.send-link').on('click', function () {
    var text = messagebar.getValue().replace(/\n/g, '<br>').trim();
    // return if empty message
    if (!text.length) return;

    // Clear area
    messagebar.clear();

    // Return focus to area
    messagebar.focus();
    console.log(user);
    console.log(to);
    messagesTo(user, to, text);
    // Add message to messages
    messages.addMessage({
      text: text,
      avatar: user.avatarUrl
    });


  });



});



function receiveMessage(text, from, t, type) {
  console.log("---------------------");
  console.log(to.id);
  console.log(from.id);
  console.log(t.id);


  if(t.type=='group'){



    if (to.id == t.id) {
      messages.addMessage({
        text: text,
        type: 'received',
        name: from.name,
        avatar: from.avatarUrl
      });
    }


  }else {
    if (to.id == from.id) {
      messages.addMessage({
        text: text,
        type: 'received',
        name: from.name,
        avatar: from.avatarUrl
      });
    }

  }



}


  $$(document).on('page:init', '.page[data-name="contact"]', function (e) {
    // create searchbar
    var searchbar = app.searchbar.create({
      el: '.searchbar',
      searchContainer: '.list',
      searchIn: '.item-title',
      on: {
        search(sb, query, previousQuery) {
          console.log(query, previousQuery);
        }
      }
    });
  });


  $$(document).on('page:init', '.page[data-name="profile"]', function (e) {

    initprofile();
  });

  var to = {
    name: '',
    password: '',
    time: '',
    avatarUrl: '',
    ip: '',
    deviceType: '',
    type: '',
    id: '',
    nameSpace: '',
    currId: ''
  }
  $$(document).on('page:init', '.page[data-name="home"]', function (e) {

    var all = "";
    $.each(onlines, function (index, obj) {

      var dom = document.getElementById(obj.id);
      if (!dom) {
        var s = addOnlineUser(obj);
        all += s;
      }
    });

    $("#messages-wrapper").append(all);


  });

  function touser(id) {


    $.each(onlines, function (index, obj) {
      console.log("********" + obj.id +" " +obj.name);
      console.log("********" + obj.id +" " +id);
      if (obj.id == id) {
        to = obj;



      var a = "messages_a_"+id;
       var div= "messages_div_"+id;

        document.getElementById(a).setAttribute("href","/messages/");
        document.getElementById(div).click();
        document.getElementById(a).setAttribute("href","#");
        return;
      }

    });


  }

  function addOnlineUser(obj) {
    var userbar = "<li class=\"swipeout\" id=\"" + obj.id + "\">\n" +
      "          <div class=\"swipeout-content\">\n" +
      "            <a href=\"#\" id=\"messages_a_"+obj.id+"\" onclick=\"touser('" + obj.id + "')\" class=\"messageclick item-link item-content\">\n" +
      "              <div id=\"messages_div_"+obj.id+"\" class=\"item-media\">\n" +
      "                <img class=\"user-avatar\" src=\"" + obj.avatarUrl + "\" width=\"44\" />\n" +
      "                <span class=\"user-online-badge\"></span>\n" +
      "              </div>\n" +
      "              <div class=\"item-inner\">\n" +
      "                <div class=\"item-title-row\">\n" +
      "                  <div class=\"item-title\">" + obj.name + "</div>\n" +
      "                  <div class=\"item-after\">" + friendlyTime(obj.time) + "</div>\n" +
      "                </div>\n" +
      "                <div class=\"item-text\">\n" +

      "                </div>\n" +
      "              </div>\n" +
      "            </a>\n" +
      "          </div>\n" +
      "          <div class=\"swipeout-actions-left\">\n" +
      "          </div>\n" +
      "          <div class=\"swipeout-actions-right\">\n" +
      "            <a href=\"#\" data-confirm=\"Are you sure you want to delete this conversation?\"\n" +
      "               class=\"swipeout-delete swipeout-overswipe\"><i class=\"mdi mdi-delete\"></i>\n" +
      "              <p>Delete</p>\n" +
      "            </a>\n" +
      "          </div>\n" +
      "        </li>";
    return userbar;
  }

  function delOnlineUser(obj) {
    var ids = obj.id;

    if (ids) {
      document.getElementById(ids).remove();
    }
  }





