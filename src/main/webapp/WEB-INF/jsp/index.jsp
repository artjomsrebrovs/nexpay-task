<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous"
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.3.min.js"
        integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>

<html>
<head>
    <title>Country Codes</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center h-50">
        <div class="card" style="width: 35rem;">
            <div class="card-body">

                <div class="card-header text-center">
                    <div class="row align-items-center">
                        <div>
                            <h5>Enter your phone number and country code</h5>
                        </div>
                    </div>
                </div>

                <div>
                    <div class="col-auto mt-3">
                        <label class="sr-only" for="code">Country Code</label>
                        <div class="input-group mb-2">
                            <div class="input-group-prepend">
                                <div class="input-group-text">+</div>
                            </div>
                            <input type="text" class="form-control" id="code" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone">Phone Number</label>
                        <input type="text" class="form-control" id="phone" placeholder="">
                    </div>

                    <button id="submit" type="submit" class="btn btn-primary mt-3">Submit</button>
                </div>

            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row justify-content-center align-items-center">
        <div class="card" style="width: 35rem;">
            <div class="card-body">
                <div class="card-header text-center">
                    <div class="row align-items-center">
                        <div>
                            <span id="result">...</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

<script type="text/javascript">
    $(document).ready(function () {

        $('#submit').click(function () {
            var phoneNumber = {
                code: $("#code").val(),
                number: $("#phone").val()
            };

            var resultSpan = $('#result');

            $.ajax({
                url: '/country',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(phoneNumber),

                success: function (data) {
                    if (data.length > 0) {
                        resultSpan.empty();
                    }

                    for (var i = 0; i < data.length; i++) {
                        resultSpan.append('<div class="row"><h5 class="text-center">' + data[i] + '</h5></div>').append('\n');
                    }
                },
                error: function(e) {
                    if (e.responseJSON.length > 0) {
                        resultSpan.empty();
                    }

                    for (var i = 0; i < e.responseJSON.length; i++) {
                        resultSpan.append('<div class="row"><h5 class="text-center text-danger">' + e.responseJSON[i] + '</h5></div>').append('\n');
                    }
                }
            });
        });
    });
</script>