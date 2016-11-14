<!-- include header start (leave me alone) -->
<jsp:include page='header.jsp'/>
<!-- include header end -->

<!-- page content start (customise) -->
<h1>Make a claim</h1>

<form action="claim" method="post">
    <div class="form-group">
        <label for="amount">Amount</label>
        <input type="text" name="amount" class="form-control" id="amount" placeholder="">
    </div>
    <div class="form-group">
        <label for="rationale">Rationale</label>
        <input type="text" name="rationale" class="form-control" id="rationale" placeholder="">
    </div>
<br>
Don't edit this page. Use it as a template to base other pages from.
<!-- page content end -->

<!-- include footer start (leave me alone) -->
<jsp:include page='footer.jsp'/>
<!-- include header end -->