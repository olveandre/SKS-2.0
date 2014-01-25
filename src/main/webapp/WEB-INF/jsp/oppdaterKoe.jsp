<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table class="table table-hover" >
            <thead>
            <tr>
                <th>Tid</th>
                <th>Navn</th>
                <th>&Oslash;ving</th>
                <th>Kommentar</th>
                <th>Sitteplass</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="koegrupper" items="${grupper}" varStatus="status">
                <tr
                        <c:if test="${koegrupper.faarHjelp!=null}">
                            class="success">
                            <td><a class="faarHjelpKnapp btn btn-success btn-sm " data-placement="top" data-toggle="popover"
                                   title="" data-content="<c:out value="${status}"/> navn"
                                   data-original-title="Får hjelp av"><i class="glyphicon glyphicon-eye-open"></i> </a></td>
                        </c:if>
                        <c:if test="${koegrupper.faarHjelp==null}">
                            >
                            <td><c:out value="${koegrupper.klokkeslett}"/></td>
                        </c:if>
                <td>
                    <c:if test="${fn:length(koegrupper.medlemmer)==1}">
                        <span class="glyphicon glyphicon-user"></span>
                    </c:if>
                    <c:if test="${fn:length(koegrupper.medlemmer)>1}">
                        <span class="iconContainer"></span>
                    </c:if>

                    <c:out value="${koegrupper.medlemmer[0].fornavn}"/> <c:out
                        value="${koegrupper.medlemmer[0].etternavn}"/></td>
                <td><c:out value="${koegrupper.getOvingerIString()}"/></td>
                <td><c:out value="${koegrupper.kommentar}"/></td>
                <td><c:out value="${koegrupper.sitteplass}"/>, bord <c:out value="${koegrupper.bordnr}"/></td>
                <td>
                    <div class="btn-group" id="<c:out value="${koegrupper.gruppeID}"/>">
                        <c:if test="${sessionScope.innloggetBruker.rettighet<3}">
                            <button class="btn btn-primary" data-task="choose" title="Velg" onclick="velgGruppeFraKoe(${koegrupper.koe_id},${koegrupper.gruppeID})"><i class="glyphicon glyphicon-edit"></i>
                            </button>
                        </c:if>
                        <c:if test="${sessionScope.innloggetBruker.rettighet==3 && koegrupper.medlemmer[0].equals(sessionScope.innloggetBruker)}">
                            <button class="btn btn-warning" data-task="edit" title="Endre &oslash;vinger"
                                    onclick="endreBruker(this.parentNode.id)"><i class="glyphicon glyphicon-edit"></i>
                            </button>
                            <button class="btn btn-danger" data-task="remove" title="Fjern"
                                    onclick="slettBruker(this.parentNode.id)"><i class="glyphicon glyphicon-remove"></i>
                            </button>
                        </c:if>
                    </div>
                </td>
                </tr>
            </c:forEach>
            </tbody>
</table>