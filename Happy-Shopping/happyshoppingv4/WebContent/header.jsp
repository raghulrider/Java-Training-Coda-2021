<div class="header">
	<form action="gotohome.do" method="post" id="home">
			<input type="hidden" name="formid" value="gotohome">
			<a onclick="document.getElementById('home').submit();" class="logo">Happy Shopping</a>
		</form>
	
	<div class="header-right">
		<form style="display: inline-block" action="allreport.do"
			method="post">
			<input type="hidden" name="formid" value="allreport">
			<button class="btn btn-primary" type="submit">All Report</button>
		</form>
		<form style="display: inline-block" action="logout.do" method="post">
			<input type="hidden" name="formid" value="logout">
			<button class="btn btn-danger" type="submit">Logout</button>
		</form>
	</div>
</div>