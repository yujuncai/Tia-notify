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


$$(document).on('page:init', '.page[data-name="messages"]', function (e) {
  // Init Messages
  var messages = app.messages.create({
    el: '.messages',

    // First message rule
    firstMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;
      /* if:
        - there is no previous message
        - or previous message type (send/received) is different
        - or previous message sender name is different
      */
      if (!previousMessage || previousMessage.type !== message.type || previousMessage.name !== message.name) return true;
      return false;
    },
    // Last message rule
    lastMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;
      /* if:
        - there is no next message
        - or next message type (send/received) is different
        - or next message sender name is different
      */
      if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
      return false;
    },
    // Last message rule
    tailMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;
      /* if (bascially same as lastMessageRule):
      - there is no next message
      - or next message type (send/received) is different
      - or next message sender name is different
    */
      if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
      return false;
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

    // Add message to messages
    messages.addMessage({
      text: text,
      avatar: 'img/890.jpg'
    });

    if (responseInProgress) return;
    // Receive dummy message
    receiveMessage();
  });

  // Dummy response
  var answers = [
    'Yes!'
  ]
  var people = [{
      name: 'Oswald Cobblepot',
      avatar: 'img/00.jpg'
    }
  ];

  function receiveMessage() {
    responseInProgress = true;
    setTimeout(function () {
      // Get random answer and random person
      var answer = answers[Math.floor(Math.random() * answers.length)];
      var person = people[Math.floor(Math.random() * people.length)];

      // Show typing indicator
      messages.showTyping({
        header: person.name + ' is typing',
        avatar: person.avatar
      });

      setTimeout(function () {
        // Add received dummy message
        messages.addMessage({
          text: answer,
          type: 'received',
          name: person.name,
          avatar: person.avatar
        });
        // Hide typing indicator
        messages.hideTyping();
        responseInProgress = false;
      }, 3000);
    }, 1000);
  }
});

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


$$(document).on('page:init', '.page[data-name="home"]', function (e) {
      $.each(onlines,function (index,value)  {

       /* <li className="swipeout">
          <div className="swipeout-content">
            <a href="/messages/" className="item-link item-content">
              <div className="item-media">
                <img className="user-avatar" src="img/230.jpg" width="44"/>
                <span className="user-online-badge"></span>
              </div>
              <div className="item-inner">
                <div className="item-title-row">
                  <div className="item-title">Edward Nygma</div>
                  <div className="item-after">Yesterday</div>
                </div>
                <div className="item-text">
                  No body, no crime
                </div>
              </div>
            </a>
          </div>
          <div className="swipeout-actions-left">
          </div>
          <div className="swipeout-actions-right">
            <a href="#" data-confirm="Are you sure you want to delete this conversation?"
               className="swipeout-delete swipeout-overswipe"><i className="mdi mdi-delete"></i>
              <p>删除</p>
            </a>
          </div>
        </li>*/
       $("#messages-wrapper").append()

  })

});
