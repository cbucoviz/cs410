$(function () {
    
    $('div.ticker marquee').marquee('pointer').mouseover(function () {
        $(this).trigger('stop');
    }).mouseout(function () {
        $(this).trigger('start');
    });
});
