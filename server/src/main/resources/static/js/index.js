"use strict";

/**
 * Initializes uptime, labels and chart values
 */
function indexInitialization()
{



    initSocket();

    logoPage = document.getElementById("logo-page");
    contactsPage = document.getElementById("contacts-page");

    showCards();

    currentClockSpeed = document.getElementById("currentClockSpeed");
    currentProcCount = document.getElementById("currentProcCount");
    currentTotalStorage = document.getElementById("currentTotalStorage");
    currentDiskCount = document.getElementById("currentDiskCount");

    currentPage = 1;
    firstControl = document.getElementById("first-control");
    secondControl = document.getElementById("second-control");

    cloudLeft = document.getElementById("cloud-left");
    cloudRight = document.getElementById("cloud-right");

    days = document.getElementById("uptime-days");
    hours = document.getElementById("uptime-hours");
    minutes = document.getElementById("uptime-minutes");
    seconds = document.getElementById("uptime-seconds");

   // usageXHR = new XMLHttpRequest();
  //  infoXHR = new XMLHttpRequest();
   // uptimeXHR = new XMLHttpRequest();

    //sendUsageRequest();

    firstControl.addEventListener("click", function(event) {changePage(event.target || event.srcElement)});
    secondControl.addEventListener("click", function(event) {changePage(event.target || event.srcElement)});
}

/**
 * Changes cards opacity with random sequence
 */
function showCards()
{
    contactsPage.style.visibility = "hidden";

    let cards = document.getElementsByClassName("card");
    let versionLabel = document.getElementById("project-version");

    let randomSequenceArray = getRandomSequenceArray();

    for (let i = 0; i < cards.length; i++)
    {
        setTimeout(function()
        {
            cards[randomSequenceArray[i]].style.opacity = "1";

            if (randomSequenceArray[i] == 4)
            {
                versionLabel.style.opacity = "1";
            }
        }, 70 * i);
    }
}

/**
 * Generates random sequence
 */
function getRandomSequenceArray()
{
    let buffer = [];

    while (buffer.length < 5)
    {
        let randomNumber = Math.floor(Math.random() * 5);

        if ((buffer.indexOf(randomNumber) === -1))
        {
            buffer.push(randomNumber);
        }
    }

    return buffer;
}






/**
 * Sending ajax request to receive usage info
 */
function sendRequest(data,type)
{
    console.log("------sendRequest------- "+type);


        if (type=='monitor_usage')
        {
            let response = data;
            console.log("------monitor_usage------- ");
            labelsTick(response);
            chartTick(response);

        }


        if (type=='monitor_info')
    {
        let response = data;
        console.log("------monitor_info------- ");
        currentClockSpeed.innerHTML = response.processor.clockSpeed;
        currentProcCount.innerHTML = response.machine.procCount;
        currentTotalStorage.innerHTML = response.storage.total;
        currentDiskCount.innerHTML = response.storage.diskCount;


    }



    if (type=='monitor_uptime')
    {
        let response = data;
        console.log("------monitor_uptime------- ");
        days.innerHTML = response.days;
        hours.innerHTML = response.hours;
        minutes.innerHTML = response.minutes;
        seconds.innerHTML = response.seconds;


    }


}





/**
 * Changes page
 *
 * @param {*} control element
 */
function changePage(element)
{
    if ((String(element.id) == "first-control") && (currentPage > 1))
    {
        currentPage -= 1;
        setCloudAnimation(currentPage);
    }
    else if ((String(element.id) == "second-control") && (currentPage < 2))
    {
        currentPage += 1;
        setCloudAnimation(currentPage);
    }

    setPageVisibility(currentPage);
    setControlOpacity(currentPage);
}

/**
 * Changes page visibility
 *
 * @param {*} new page
 */
function setPageVisibility(newPage)
{
    switch (newPage)
    {
        case 1:
        {
            logoPage.style.visibility = "";
            contactsPage.style.visibility = "hidden";
            break;
        }
        case 2:
        {
            logoPage.style.visibility = "hidden";
            contactsPage.style.visibility = "";
            break;
        }
    }
}

/**
 * Animates clouds
 *
 * @param {*} new page
 */
function setCloudAnimation(newSquareScale)
{
    switch (newSquareScale)
    {
        case 1:
        {
            cloudLeft.style.animation = "fade-in-cloud-left 0.3s forwards";
            cloudRight.style.animation = "fade-in-cloud-right 0.3s forwards";
            break;
        }
        case 2:
        {
            cloudLeft.style.animation = "fade-out-cloud-left 0.3s forwards";
            cloudRight.style.animation = "fade-out-cloud-right 0.3s forwards";
            break;
        }
    }
}

/**
 * Changes opacity of control
 *
 * @param {*} new page
 */
function setControlOpacity(newSquareScale)
{
    switch (newSquareScale)
    {
        case 1:
        {
            firstControl.style.opacity = "0.5";
            secondControl.style.opacity = "1";
            break;
        }
        case 2:
        {
            firstControl.style.opacity = "1";
            secondControl.style.opacity = "0.5";
            break;
        }
    }
}