import { useLocation } from "react-router-dom";
import React, { useState } from "react";

import {
  Box, Button, Dialog, DialogActions, DialogContent, DialogContentText,
  DialogTitle,
  useMediaQuery
} from "@mui/material";

import { Formik } from "formik";
import * as yup from "yup";

import EmployeesFormEditInputs from "../../components/formEditInputs/Employees";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import { Constants } from "../../data/constants";

const employeeSchema = yup.object().shape({
  lastName: yup.string().max(50).required(),
  firstName: yup.string().max(50).required(),
  email: yup.string().email().max(50).required(),
  reportsTo: yup.number(),
  jobTitle: yup.string().max(50).required(),
  extension: yup.string().max(10).required(),
  officeId: yup.number().required(),
})

const FormEditEmployee = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls(rowData.id);
  const jobTitleList = Constants().employee.jobTitle;

  const [dataIdName, setDataIdName] = useState(null);
  FormListCalls(url.employee.findByIdNames, setDataIdName);

  const [dataOfficeIdName, setDataOfficeIdName] = useState(null);
  FormListCalls(url.offices.findByOfficeIds, setDataOfficeIdName);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();

  const initialValues = {
    lastName: rowData.lastName, firstName: rowData.firstName,
    email: rowData.email, reportsTo: rowData.reportsTo, jobTitle:
      rowData.jobTitle, extension: rowData.extension, officeId: rowData.officeId
  };


}

export default FormEditEmployee
