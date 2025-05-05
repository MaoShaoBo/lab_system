
//实验室类型
const LabType = [
    {
        label: "计算机实验室",
        value: 1,
    },
    {
        label: "电信实验室",
        value: 2,
    },
    {
        label: "化学实验室",
        value: 3,
    }, {
        label: "电子设计实验室",
        value: 4,
    },
    {
        label: "生命科学研究实验室",
        value: 5,
    },
    {
        label: "食品实验室",
        value: 6,
    },
    {
        label: "材料实验室",
        value: 7,
    },
    {
        label: "通用实验室",
        value: 8,
    }
]

const CollegeType = [
    {
        label: "电智学院",
        value: 1,
    },
    {
        label: "食品学院",
        value: 2,
    }, 
    {
        label: "材料学院",
        value: 3,
    }, {
        label: "经济管理学院",
        value: 4,
    },
    {
        label: "人文学院",
        value: 5,
    }
]
const CollegeType2 = [
    {
        label: "电智学院",
        value: "电智学院",
    },
    {
        label: "食品学院",
        value: "食品学院",
    }, 
    {
        label: "材料学院",
        value: "材料学院",
    }, {
        label: "经济管理学院",
        value: "经济管理学院",
    },
    {
        label: "人文学院",
        value: "人文学院",
    }
]

 const  LabColorType = {
    1:"#02909d",
    2:"#eb5c20",
    3:"#a1d4bd",
    4:"#d05667",
    5:"#8a2be2" 
}

 const  CollegesColorType = {
    1:"#fbcb4a",
    2:"#14c145",
    3:"#eedeb0",
    4:"#065279",
    5:"#ff69b4" 
}

const ClassType = [
    {
        label:"第一节课",
        value:1
    },
    {
        label:"第二节课",
        value:2
    }, {
        label:"第三节课",
        value:3
    },
    {
        label:"第四节课",
        value:4
    }
]

const GadgetClassType = [
    {
        label:"上午",
        value:1
    },
    {
        label:"下午",
        value:2
    }
]

const Book_state_text=["审核中","已批准","被驳回"]
const Book_state_type=["warning","success","danger"]

const ADMIN = 1
const TEACHER = 2


const AUDIT = 0; //审核中
const APPROVE = 1; //已批准
const REJECT  =2; //被驳回

export {
    LabType,
    CollegeType,
    CollegeType2,
    LabColorType,
    CollegesColorType,
    ClassType,
    Book_state_text,
    Book_state_type,
    ADMIN,
    TEACHER,
    AUDIT,
    APPROVE,
    REJECT,
    GadgetClassType
}