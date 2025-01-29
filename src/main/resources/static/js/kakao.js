var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(35.91124391258411, 128.80795633875374), // 지도의 중심좌표
        draggable: false, // 확대 축소 및 드래그를 통한 이동 불허
        level: 5 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


// 지도에 교통정보를 표시하도록 지도타입을 추가합니다
map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);

