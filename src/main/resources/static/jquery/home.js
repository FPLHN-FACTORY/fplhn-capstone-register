$(document).ready(function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
    $('#btn_login').click(function (e) {
        e.preventDefault();
        $('.msg').text('');
        let t = $(this), r = $(this).closest("form");
        r.validate({
            rules: {
                username: {required: !0,},
                password: {required: !0,},
                campus_id: {required: !0,},
            },
            messages: {
                username: {required: 'Ná»™i dung khĂ´ng Ä‘Æ°á»£c bá» trá»‘ng',},
                password: {required: 'Ná»™i dung khĂ´ng Ä‘Æ°á»£c bá» trá»‘ng',},
                campus_id: {required: 'Báº¡n chÆ°a chá»n cÆ¡ sá»Ÿ',},
            },
        });
        r.valid() && (
            $.ajax({
                data: {
                    username: $('#username').val(),
                    password: $('#password').val(),
                    campus_id: $('#campus_id').val(),
                },
                url: r.attr('action'),
                type: 'post',
                beforeSend: function () {
                    $('#exampleModalCenter').block({
                        css: {
                            border: 'none',
                            padding: '15px',
                            backgroundColor: '#000',
                            '-webkit-border-radius': '10px',
                            '-moz-border-radius': '10px',
                            opacity: .5,
                            color: '#fff'
                        },
                        message: 'Äang kiá»ƒm tra thĂ´ng tin cá»§a báº¡n...'
                    });
                },
                success: function (r, s, x) {
                    if (r.status == 'error') {
                        $('.msg').text(r.msg);
                    } else {
                        location.href = '/sinh-vien';
                    }
                },
                error: function () {

                },
                complete: function () {
                    $('#exampleModalCenter').unblock();
                }
            })
        );
    });
    $('#btn_login_google').click(function (e) {
        let campus_id = $('#campus_id').val();
        let t = $(this), r = $(this).closest("form");
        r.validate({
            rules: {
                campus_id: {required: !0,},
            },
            messages: {
                campus_id: {required: 'Báº¡n chÆ°a chá»n cÆ¡ sá»Ÿ',},
            },
        });
        r.valid() && (location.href = '/login/google?campus_id=' + campus_id);

    });
});